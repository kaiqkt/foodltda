package authorizationservice.resources.security

import authorizationservice.domain.entities.Credentials
import authorizationservice.domain.entities.AuthSessionDetail
import authorizationservice.domain.repositories.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.jvm.Throws

class AuthenticationFilter(jwtUtil: JWTUtil, authenticationManager: AuthenticationManager, userRepository: UserRepository) : UsernamePasswordAuthenticationFilter() {
    private val jwtUtil: JWTUtil
    private val userRepository: UserRepository

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        return try {
            val user = ObjectMapper().readValue(request.inputStream, Credentials::class.java)
            val authToken = UsernamePasswordAuthenticationToken(user.username, user.password, ArrayList())

            authenticationManager.authenticate(authToken)
        } catch (e: IOException) {
            throw RuntimeException()
        }
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain, authResult: Authentication) {
        val username: String = (authResult.principal as UserDetailsImpl).username

        val user = userRepository.findByEmail(username)
        val token = jwtUtil.generateToken(user?.personId)

        response.contentType = "application/json"
        response.writer.append(user.toString())
        response.addHeader("Authorization", "Bearer $token")
        response.addHeader("access-control-expose-headers", "Authorization")
    }

    private fun sessionDetails(request: HttpServletRequest, token: String, personId: String, username: String) {
        val sessionUser = AuthSessionDetail(
            username = username,
            personId = personId,
            channel = request.getHeader("Channel"),
            ip = request.getHeader("X-FORWARDED-FOR") ?: request.remoteAddr,
            token = token
        )
    }

    private inner class JWTAuthenticationFailureHandler : AuthenticationFailureHandler {
        @Throws(IOException::class, ServletException::class)
        override fun onAuthenticationFailure(request: HttpServletRequest, response: HttpServletResponse, exception: AuthenticationException) {
            response.status = 401
            response.contentType = "application/json"
            response.writer.append(json())
        }

        private fun json(): String {
            val date = Date().time
            return ("{\"timestamp\": " + date + ", "
                    + "\"status\": 401, "
                    + "\"error\": \"Unauthorized\", "
                    + "\"message\": \"Email or password wrong\", "
                    + "\"path\": \"/login\"}")
        }
    }

    init {
        setAuthenticationFailureHandler(JWTAuthenticationFailureHandler())
        this.jwtUtil = jwtUtil
        this.authenticationManager = authenticationManager
        this. userRepository = userRepository
    }
}