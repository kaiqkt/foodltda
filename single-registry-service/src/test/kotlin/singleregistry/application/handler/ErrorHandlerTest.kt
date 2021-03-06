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
    fun `given an errorHandler when handling a LegalPersonNotFoundException should return HTTP status 404`() {
        val request = webRequest

        val errorHandler = ErrorHandler()
        val expectedMessage = "error"
        val legalPersonNotFoundException = LegalPersonNotFoundException(expectedMessage)

        val response = errorHandler.handleLegalPersonNotFoundException(legalPersonNotFoundException, request)

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        Assertions.assertNotNull(response)
    }

    @Test
    fun `given an errorHandler when handling a PersonNotFoundException should return HTTP status 404`() {
        val request = webRequest

        val errorHandler = ErrorHandler()
        val expectedMessage = "error"
        val personNotFoundException = PersonNotFoundException(expectedMessage)

        val response = errorHandler.handlePersonNotFoundException(personNotFoundException, request)

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        Assertions.assertNotNull(response)
    }

    @Test
    fun `given an errorHandler when handling a IndividualPersonNotFoundException should return HTTP status 404`() {
        val request = webRequest

        val errorHandler = ErrorHandler()
        val expectedMessage = "error"
        val individualPersonNotFoundException = IndividualPersonNotFoundException(expectedMessage)

        val response = errorHandler.handleIndividualPersonNotFoundException(individualPersonNotFoundException, request)

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        Assertions.assertNotNull(response)
    }
}
