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
        jwtUtil = JWTUtil("teste", "60000", userRepository)
    }

    @Test
    fun `given a person id, should be generate token with success`() {
        val personId =  "7568"

        val token = jwtUtil.generateToken(personId)

        Assertions.assertEquals(personId, jwtUtil.getPersonId(token))
    }

    @Test
    fun `given a token when is valid, should be return true`() {
        val personId =  "7568"

        every { userRepository.existsByPersonId(personId) } returns true

        val token = jwtUtil.generateToken(personId)

        Assertions.assertTrue(jwtUtil.validToken(token))
    }

    @Test
    fun `given a token when is invalid, should be return false`() {
        val personId =  "7568"

        every { userRepository.existsByPersonId(personId) } returns false

        val token = jwtUtil.generateToken(personId)

        Assertions.assertFalse(jwtUtil.validToken(token))
    }
}