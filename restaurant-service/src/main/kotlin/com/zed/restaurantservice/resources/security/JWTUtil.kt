package com.zed.restaurantservice.resources.security

import com.zed.restaurantservice.resources.singleregistry.gateways.SingleRegistryServiceImpl
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JWTUtil(
    @Value("\${jwt.secret}") private var secret: String,
    @Value("\${jwt.expiration}") private var expiration: String,
    private val singleRegistryServiceImpl: SingleRegistryServiceImpl
) {

    fun generateToken(personId: String?, email: String?): String {
        return Jwts.builder()
            .setId(personId)
            .setSubject(email)
            .setExpiration(Date(System.currentTimeMillis() + expiration.toLong()))
            .signWith(SignatureAlgorithm.HS512, secret.toByteArray())
            .compact()
    }

    fun validToken(token: String): Boolean {
        val claims = getClaims(token)
        if (claims != null) {
            val personId = claims.id
            val person = singleRegistryServiceImpl.findByPersonId(personId)
            val expirationDate = claims.expiration
            val now = Date(System.currentTimeMillis())
            return personId != null && expirationDate != null && now.before(expirationDate) && person != null
        }
        return false
    }

    fun getPersonId(token: String): String? {
        val claims = getClaims(token)
        return claims?.id
    }

    fun getUsername(token: String): String? {
        val claims = getClaims(token)
        return claims?.subject
    }

    private fun getClaims(token: String): Claims? {
        return try {
            Jwts.parser().setSigningKey(secret.toByteArray()).parseClaimsJws(token).body
        } catch (e: Exception) {
            null
        }
    }
}