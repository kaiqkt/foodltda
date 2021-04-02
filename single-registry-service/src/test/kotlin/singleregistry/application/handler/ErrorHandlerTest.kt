package singleregistry.application.handler

import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.web.context.request.WebRequest
import singleregistry.domain.exceptions.*


class ErrorHandlerTest {

    private lateinit var webRequest: WebRequest

    @BeforeEach
    fun beforeEach() {
        webRequest = mockk(relaxed = true)
    }

    @Test
    fun `given an errorHandler when handling a DataValidationException should return HTTP status 400`() {
        val request = webRequest

        val errorHandler = ErrorHandler()
        val expectedDetails = emptyList<String>()
        val dataValidationException = DataValidationException(expectedDetails)

        val response = errorHandler.handleDataAlreadyInUseException(dataValidationException, request)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        Assertions.assertNotNull(response)
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
    fun `given an errorHandler when handling a CommunicationException should return HTTP status 400`() {
        val request = webRequest

        val errorHandler = ErrorHandler()
        val expectedDetails = "error"
        val communicationException = CommunicationException(expectedDetails)

        val response = errorHandler.handleCommunicationException(communicationException, request)

        Assertions.assertEquals(HttpStatus.BAD_GATEWAY, response.statusCode)
        Assertions.assertNotNull(response)
    }
}
