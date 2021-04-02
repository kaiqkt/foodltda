package authorizationservice.doman.service

import authorizationservice.domain.repositories.UserRepository
import authorizationservice.resources.security.UserDetailsServiceImpl
import authorizationservice.factories.UserFactory
import authorizationservice.resources.security.UserDetailsImpl
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.core.userdetails.UsernameNotFoundException

class UserDetailsServiceTest {
    private lateinit var userDetailsService: UserDetailsServiceImpl
    private lateinit var userRepository: UserRepository

    @BeforeEach
    fun beforeEach() {
        userRepository = mockk(relaxed = true)
        userDetailsService = UserDetailsServiceImpl(userRepository)
    }

    @Test
    fun `given a valid user, should be return UserDetailsImpl`() {
        val user = UserFactory.sample()
        val userDetails = UserDetailsImpl(user)

        every { userRepository.findByEmail(user.email) } returns user

        val response = userDetailsService.loadUserByUsername(user.email)

        Assertions.assertEquals(userDetails.username, response.username)
        Assertions.assertEquals(userDetails.password, response.password)
    }

    @Test
    fun `given a invalid user, should be return UsernameNotFoundException`() {
        val user = UserFactory.sample()
        val error = "User:${user.email} not found"

        every { userRepository.findByEmail(user.email) } returns null

        val response = assertThrows<UsernameNotFoundException> {
            userDetailsService.loadUserByUsername(user.email)
        }

        Assertions.assertEquals(error, response.message)
    }
}