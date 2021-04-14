package com.zed.restaurantservice.resources.security

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.jvm.Throws

class AuthorizationFilter(
    authenticationManager: AuthenticationManager?,
    private val jwtUtil: JWTUtil,
    private val secret: String
) :
    BasicAuthenticationFilter(authenticationManager) {

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val header = request.getHeader("Authorization")
        header?.let {
            val auth = if (it.startsWith("Bearer ")) {
                getJWTAuthentication(header.substring(7))
            } else {
                getAuthentication(header)
            }

            if (auth != null) {
                SecurityContextHolder.getContext().authentication = auth
            }
        }

        chain.doFilter(request, response)
    }

    private fun getJWTAuthentication(token: String): UsernamePasswordAuthenticationToken? {
        if (jwtUtil.validToken(token)) {
            val username = jwtUtil.getUsername(token)
            return UsernamePasswordAuthenticationToken(
                username,
                null,
                mutableListOf<GrantedAuthority>(SimpleGrantedAuthority("ROLE_USER"))
            )
        }
        return null
    }

    private fun getAuthentication(token: String): UsernamePasswordAuthenticationToken? {
        if (secret == token) {
            val user = "auth_service"
            return UsernamePasswordAuthenticationToken(
                user, null,
                mutableListOf<GrantedAuthority>(SimpleGrantedAuthority("ROLE_ADM"))
            )
        }
        return null
    }

}



