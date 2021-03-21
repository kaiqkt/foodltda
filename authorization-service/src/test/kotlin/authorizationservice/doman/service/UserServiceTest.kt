package authorizationservice.doman.service

import authorizationservice.domain.exceptions.DataValidationException
import authorizationservice.domain.repositories.UserRepository
import authorizationservice.domain.services.UserService
import authorizationservice.factories.UserFactory
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class UserServiceTest {
    private lateinit var userRepository: UserRepository
    private lateinit var userService: UserService
    private lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder

    @BeforeEach
    fun beforeEach() {
        userRepository = mockk(relaxed = true)
        bCryptPasswordEncoder = mockk(relaxed = true)
        userService = UserService(userRepository, bCryptPasswordEncoder)
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

}