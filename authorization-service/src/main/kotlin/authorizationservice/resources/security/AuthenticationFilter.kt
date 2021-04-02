package authorizationservice.resources.security

import authorizationservice.domain.entities.AuthSession
import authorizationservice.domain.entities.Channel
import authorizationservice.domain.entities.Login
import authorizationservice.domain.entities.User
import authorizationservice.domain.exceptions.LoginException
import authorizationservice.domain.repositories.RedisSessionRepository
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
import java.util.concurrent.TimeUnit
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter(
    jwtUtil: JWTUtil,
    authenticationManager: AuthenticationManager,
    userRepository: UserRepository,
    redisSessionRepository: RedisSessionRepository,
    expiration: String
) : UsernamePasswordAuthenticationFilter() {

    private val jwtUtil: JWTUtil
    private val userRepository: UserRepository
    private val redisRepository: RedisSessionRepository
    private val expiration: String

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        return try {
            val user = ObjectMapper().readValue(request.inputStream, Login::class.java)
            val authToken = UsernamePasswordAuthenticationToken(user.email, user.password, ArrayList())

            authenticationManager.authenticate(authToken)
        } catch (e: IOException) {
            throw LoginException("Invalid Authentication")
        }
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        val username: String = (authResult.principal as UserDetailsImpl).username

        val user = userRepository.findByEmail(username)
        val token = jwtUtil.generateToken(user?.personId, user?.email)

        sessionDetails(request, token, user!!)

        response.contentType = "application/json"
        response.addHeader("Authorization", "Bearer $token")
        response.addHeader("access-control-expose-headers", "Authorization")
    }

    private fun sessionDetails(request: HttpServletRequest, token: String, user: User) {
        val sessionUser = AuthSession(
            userId = user._id,
            username = user.email,
            personId = user.personId,
            channel = Channel.valueOf(request.getHeader("CHANNEL")),
            ip = request.getHeader("X-FORWARDED-FOR") ?: request.remoteAddr,
            token = token,
            expiration = expiration.toLong(),
            timeUnit = TimeUnit.HOURS
        )

        redisRepository.createSession(sessionUser)
    }

    private inner class JWTAuthenticationFailureHandler : AuthenticationFailureHandler {
        @Throws(IOException::class, ServletException::class)
        override fun onAuthenticationFailure(
            request: HttpServletRequest,
            response: HttpServletResponse,
            exception: AuthenticationException
        ) {
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
        this.userRepository = userRepository
        this.redisRepository = redisSessionRepository
        this.expiration = expiration
    }
}