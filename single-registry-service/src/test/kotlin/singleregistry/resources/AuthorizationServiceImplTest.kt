package singleregistry.resources

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import singleregistry.resources.authorization.clients.AuthorizationServiceClient
import singleregistry.resources.authorization.entities.User
import singleregistry.resources.authorization.gateways.AuthorizationServiceImpl

class AuthorizationServiceImplTest {
    private lateinit var authorizationServiceImpl: AuthorizationServiceImpl
    private lateinit var authorizationServiceClient: AuthorizationServiceClient
    private lateinit var token: String

    @BeforeEach
    fun beforeEach() {
        authorizationServiceClient = mockk(relaxed = true)
        token = "SOME_TOKEN"
        authorizationServiceImpl = AuthorizationServiceImpl(authorizationServiceClient, token)
    }

    @Test
    fun `given a valid user, should return status http 200`() {
        val user = User(
            personId = "1234",
            email = "test@test.com",
            password = "12354",
            countryCode = "+55",
            areaCode = "11",
            phoneNumber = "940028922"
        )

        val responseEntity = ResponseEntity<User>(user, HttpStatus.CREATED)

        every { authorizationServiceClient.register(user, token) } returns responseEntity

        authorizationServiceImpl.register(user)

        verify { authorizationServiceClient.register(any(), any()) }
        Assertions.assertEquals(responseEntity, authorizationServiceClient.register(user, token))
    }
}