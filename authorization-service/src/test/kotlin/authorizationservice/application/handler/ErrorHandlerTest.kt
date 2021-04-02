package authorizationservice.application.handler

import authorizationservice.domain.exceptions.*
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.context.request.WebRequest

class ErrorHandlerTest {

    private lateinit var webRequest: WebRequest

    @BeforeEach
    fun beforeEach() {
        webRequest = mockk(relaxed = true)
    }

    @Test
    fun `given an errorHandler when handling a ResultBindingException should return HTTP status 400`() {
        val request = webRequest

        val errorHandler = ErrorHandler()
        val expectedDetails = emptyList<String>()
        val resultBindingException = ResultBindingException(expectedDetails)

        val response = errorHandler.handleResultBindingException(resultBindingException, request)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        Assertions.assertNotNull(response)
    }

    @Test
    fun `given an errorHandler when handling a DataValidationException should return HTTP status 400`() {
        val request = webRequest

        val errorHandler = ErrorHandler()
        val expectedDetails = emptyList<String>()
        val dataValidationException = DataValidationException(expectedDetails)

        val response = errorHandler.handleDataValidationException(dataValidationException, request)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        Assertions.assertNotNull(response)
    }

    @Test
    fun `given an errorHandler when handling a UsernameNotFoundException should return HTTP status 403`() {
        val request = webRequest

        val errorHandler = ErrorHandler()
        val expectedError = "error"
        val usernameNotFoundException = UsernameNotFoundException(expectedError)

        val response = errorHandler.handleUsernameNotFoundException(usernameNotFoundException, request)

        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.statusCode)
        Assertions.assertNotNull(response)
    }

    @Test
    fun `given an errorHandler when handling a LoginException should return HTTP status 403`() {
        val request = webRequest

        val errorHandler = ErrorHandler()
        val expectedError = "error"
        val invalidLoginException = LoginException(expectedError)

        val response = errorHandler.handleLoginException(invalidLoginException, request)

        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.statusCode)
        Assertions.assertNotNull(response)
    }

    @Test
    fun `given an errorHandler when handling a SessionException should return HTTP status 400`() {
        val request = webRequest

        val errorHandler = ErrorHandler()
        val expectedError = "error"
        val invalidSessionException = SessionException(expectedError)

        val response = errorHandler.handleSessionException(invalidSessionException, request)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        Assertions.assertNotNull(response)
    }
}