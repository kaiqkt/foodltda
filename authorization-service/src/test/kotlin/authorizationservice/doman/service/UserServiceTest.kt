package authorizationservice.doman.service

import authorizationservice.domain.exceptions.DataValidationException
import authorizationservice.domain.repositories.RedisSessionRepository
import authorizationservice.domain.repositories.UserRepository
import authorizationservice.domain.services.UserService
import authorizationservice.factories.UserFactory
import authorizationservice.resources.security.UserDetailsImpl
import io.mockk.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class UserServiceTest {
    private lateinit var userRepository: UserRepository
    private lateinit var userService: UserService
    private lateinit var redisSessionRepository: RedisSessionRepository
    private lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder

    @BeforeEach
    fun beforeEach() {
        redisSessionRepository = mockk(relaxed = true)
        userRepository = mockk(relaxed = true)
        bCryptPasswordEncoder = mockk(relaxed = true)
        userService = UserService(userRepository, redisSessionRepository, bCryptPasswordEncoder)
    }

    @Test
    fun `given a valid user, should be create a user in database`() {
        val user = UserFactory.sample()

        every { userRepository.save(user) } returns user

        userService.create(user)

        verify { userRepository.save(any()) }
    }

    @Test
    fun `given a existing user email, should be return DataValidationException`() {
        val user = UserFactory.sample()
        val error = listOf("Email: ${user.email} already use")

        every { userRepository.existsByEmail(user.email) } returns true

        val response = assertThrows<DataValidationException> {
            userService.create(user)
        }

        Assertions.assertEquals(error, response.details())
    }

    @Test
    fun `given a existing user person id , should be return DataValidationException`() {
        val user = UserFactory.sample()
        val error = listOf("PersonId: ${user.personId} already use")

        every { userRepository.existsByPersonId(user.personId) } returns true

        val response = assertThrows<DataValidationException> {
            userService.create(user)
        }

        Assertions.assertEquals(error, response.details())
    }

    @Test
    fun `given a existing phone, should be return DataValidationException`() {
        val user = UserFactory.sample()
        val error = listOf(
            "Phone: ${user.phone?.countryCode}" +
                    "${user.phone?.areaCode}" +
                    "${user.phone?.number} already use"
        )

        every {
            userRepository.existsByPhone(user.phone)
        } returns true

        val response = assertThrows<DataValidationException> {
            userService.create(user)
        }

        Assertions.assertEquals(error, response.details())
    }

    @Test
    fun `given a token, should be delete session`() {
        val user = UserFactory.sample()

        every { userRepository.findByEmail(user.email) } returns user
        every { redisSessionRepository.deleteSession(user._id) } just runs

        userService.deleteSession()

        verify { redisSessionRepository.deleteSession(any()) }
    }
}