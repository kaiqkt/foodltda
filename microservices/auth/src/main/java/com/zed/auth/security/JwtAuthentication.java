package com.zed.auth.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.zed.auth.model.User;
import com.zed.auth.property.JwtConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

public class JwtAuthentication extends UsernamePasswordAuthenticationFilter {

    private JwtConfiguration jwtConfiguration;

    private AuthenticationManager authenticationManager;

    public JwtAuthentication(JwtConfiguration jwtConfiguration, AuthenticationManager authenticationManager) {
        this.jwtConfiguration = jwtConfiguration;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {

            User user = new ObjectMapper().readValue(req.getInputStream(), User.class);

            if (user == null) {
                throw new UsernameNotFoundException("Unable to retrieve the username or password");
            }
            UsernamePasswordAuthenticationToken username = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), new ArrayList<>());
            username.setDetails(user);
            return authenticationManager.authenticate(username);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        try {
            SignedJWT signedJWT = createSignedJwt(authResult);
            String encryptedToken = encryptToken(signedJWT);

            response.addHeader("Authorization", "Bearer " + encryptedToken);
            response.addHeader("access-control-expose-headers", "Authorization");
        } catch (NoSuchAlgorithmException | JOSEException | ParseException e) {
            e.printStackTrace();
        }
    }

    private SignedJWT createSignedJwt(Authentication auth) throws NoSuchAlgorithmException, JOSEException {
        User user = (User) auth.getPrincipal();
        JWTClaimsSet jwtClaimsSet = createClaimSet(auth, user);
        KeyPair key = generateKeyPair();

        JWK jwk = new RSAKey.Builder((RSAPublicKey) key.getPublic()).keyID(UUID.randomUUID().toString()).build();
        SignedJWT signedJWT = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.RS256)
                .jwk(jwk)
                .type(JOSEObjectType.JWT)
                .build(), jwtClaimsSet);
        signedJWT.sign(new RSASSASigner(key.getPrivate()));
        return signedJWT;
    }

    private JWTClaimsSet createClaimSet(Authentication auth, User user) {
        return new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .claim("authorities", auth.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(toList()))
                .issuer("http:/zed.com.br")
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + (jwtConfiguration.getExpiration() * 1000)))
                .build();
    }

    private KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");

        generator.initialize(2048);

        return generator.genKeyPair();
    }

    private String encryptToken(SignedJWT signedJWT) throws ParseException, JOSEException {
        DirectEncrypter encrypter = new DirectEncrypter(OctetSequenceKey.parse(jwtConfiguration.getSecret()));

        JWEObject jweObject =  new JWEObject(new JWEHeader.Builder(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256)
        .contentType("JWT")
        .build(), new Payload(signedJWT));

        jweObject.encrypt(encrypter);

        return jweObject.serialize();
    }
}
