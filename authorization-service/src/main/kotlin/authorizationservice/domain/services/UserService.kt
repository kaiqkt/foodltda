package authorizationservice.domain.services

import authorizationservice.domain.entities.User
import authorizationservice.domain.exceptions.DataValidationException
import authorizationservice.domain.repositories.RedisSessionRepository
import authorizationservice.domain.repositories.UserRepository
import authorizationservice.resources.security.JWTUtil
import authorizationservice.resources.security.UserDetailsImpl
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val redisSessionRepository: RedisSessionRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
) {

    private companion object {
        val logger: Logger = LoggerFactory.getLogger(UserService::class.java)
    }

    fun create(user: User): User {
        validateDate(user)

        logger.info("Creating user")

        user.password = bCryptPasswordEncoder.encode(user.password)

        val newUser = userRepository.save(user)
        logger.info("User[${newUser._id}] with mongo database created")

        return newUser
    }

    fun deleteSession() {
        val user = userRepository.findByEmail(authenticated()?.username)

        redisSessionRepository.deleteSession(user?._id)
    }

    private fun authenticated(): UserDetailsImpl? {
        return try {
            SecurityContextHolder.getContext().authentication.principal as UserDetailsImpl
        } catch (e: Exception) {
            null
        }
    }

    private fun validateDate(user: User) {
        val error = mutableListOf<String>()

        user.personId.let {
            if (userRepository.existsByPersonId(it)) {
                error.add("PersonId: $it already use")
            }
        }
        user.email.let {
            if (userRepository.existsByEmail(it)) {
                error.add("Email: $it already use")
            }
        }
        user.phone.let {
            if (userRepository.existsByPhone(it)) {
                error.add(
                    "Phone: ${user.phone?.countryCode}" +
                            "${user.phone?.areaCode}" +
                            "${user.phone?.number} already use"
                )
            }
        }

        if (error.isNotEmpty()) {
            throw DataValidationException(error)
        }
    }
}