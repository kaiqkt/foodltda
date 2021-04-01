package authorizationservice.domain.services

import authorizationservice.domain.repositories.UserRepository
import authorizationservice.resources.security.UserDetailsImpl
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val userRepository: UserRepository): UserDetailsService {
    private companion object {
        val logger: Logger = LoggerFactory.getLogger(UserDetailsServiceImpl::class.java)
    }

    override fun loadUserByUsername(email: String?): UserDetails {
        val user = userRepository.findByEmail(email) ?: throw UsernameNotFoundException("User:$email not found")

        logger.info("Load user by username:$email")

        return UserDetailsImpl(user)
    }
}