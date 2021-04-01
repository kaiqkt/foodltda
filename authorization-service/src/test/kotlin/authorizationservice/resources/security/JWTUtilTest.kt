package authorizationservice.resources.security

import authorizationservice.domain.repositories.UserRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class JWTUtilTest {
    private lateinit var jwtUtil: JWTUtil
    private lateinit var userRepository: UserRepository

    @BeforeEach
    fun beforeEach() {
        userRepository = mockk(relaxed = true)
        jwtUtil = JWTUtil("test", "60000", userRepository)
    }

    @Test
    fun `given a person id, should be generate token with success`() {
        val personId =  "7568"
        val email = "test@test.com"

        val token = jwtUtil.generateToken(personId, email)

        Assertions.assertEquals(personId, jwtUtil.getPersonId(token))
    }

    @Test
    fun `given a token when is valid, should be return true`() {
        val personId =  "7568"
        val email = "test@test.com"

        every { userRepository.existsByPersonId(personId) } returns true
        every { userRepository.existsByEmail(email) } returns true

        val token = jwtUtil.generateToken(personId, email)

        Assertions.assertTrue(jwtUtil.validToken(token))
    }

    @Test
    fun `given a token when is invalid, should be return false`() {
        val personId =  "7568"
        val email = "test@test.com"

        every { userRepository.existsByPersonId(personId) } returns false

        val token = jwtUtil.generateToken(personId, email)

        Assertions.assertFalse(jwtUtil.validToken(token))
    }
}