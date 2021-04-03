package singleregistry.application.handler

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import singleregistry.domain.exceptions.CommunicationException
import singleregistry.domain.exceptions.DataValidationException
import singleregistry.domain.exceptions.ResultBindingException
import java.time.LocalDateTime
import java.util.*


@ControllerAdvice
class ErrorHandler : ResponseEntityExceptionHandler() {

    private companion object

    val logger: Logger = LoggerFactory.getLogger(ErrorHandler::class.java.name)

    @ExceptionHandler(DataValidationException::class)
    fun handleDataAlreadyInUseException(
        ex: DataValidationException, request: WebRequest
    ): ResponseEntity<Any> {
        val uri: List<String> = request.getDescription(true).split(";")
        logger.error("data validation exception error: $uri")

        val body: MutableMap<String, Any> = LinkedHashMap()
        body["timestamp"] = LocalDateTime.now()
        body["message"] = ex.details()
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ResultBindingException::class)
    fun handleResultBindingException(
        ex: ResultBindingException, request: WebRequest
    ): ResponseEntity<Any> {
        val uri: List<String> = request.getDescription(true).split(";")
        logger.error("result binding exception error: $uri")

        val body: MutableMap<String, Any> = LinkedHashMap()
        body["timestamp"] = LocalDateTime.now()
        body["message"] = ex.details()
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(CommunicationException::class)
    fun handleCommunicationException(
        ex: CommunicationException, request: WebRequest
    ): ResponseEntity<Any> {
        val uri: List<String> = request.getDescription(true).split(";")
        logger.error("communication exception error: $uri")

        val body: MutableMap<String, Any> = LinkedHashMap()
        body["timestamp"] = LocalDateTime.now()
        body["message"] = ex.message
        return ResponseEntity(body, HttpStatus.BAD_GATEWAY)
    }
}
