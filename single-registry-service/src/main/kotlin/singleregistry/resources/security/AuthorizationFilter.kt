package singleregistry.resources.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthorizationFilter(authenticationManager: AuthenticationManager?, secret: String) :
    BasicAuthenticationFilter(authenticationManager) {

    private val authToken = secret

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val header = request.getHeader("Authorization")
        if (header != null && header.startsWith("Bearer ")) {
            val auth = getAuthentication(header.substring(7))
            if (auth != null) {
                SecurityContextHolder.getContext().authentication = auth
            }
        }
        chain.doFilter(request, response)
    }

    private fun getAuthentication(token: String): UsernamePasswordAuthenticationToken? {
        if (authToken == token) {
            val user = "auth_service"
            return UsernamePasswordAuthenticationToken(user, null, mutableListOf<GrantedAuthority>(SimpleGrantedAuthority(ROLE.ADMIN.toString())))
        }

        return null
    }

}

enum class ROLE {
    ADMIN
}


