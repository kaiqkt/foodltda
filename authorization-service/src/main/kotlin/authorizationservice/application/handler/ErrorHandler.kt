package authorizationservice.application.handler

import authorizationservice.domain.exceptions.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.LocalDateTime


@ControllerAdvice
class ErrorHandler : ResponseEntityExceptionHandler() {

    private companion object

    val logger: Logger = LoggerFactory.getLogger(ErrorHandler::class.java.name)

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

    @ExceptionHandler(DataValidationException::class)
    fun handleDataValidationException(
        ex: DataValidationException, request: WebRequest
    ): ResponseEntity<Any> {
        val uri: List<String> = request.getDescription(true).split(";")
        logger.error("data validation exception error: $uri")

        val body: MutableMap<String, Any> = LinkedHashMap()
        body["timestamp"] = LocalDateTime.now()
        body["message"] = ex.details()
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(UsernameNotFoundException::class)
    fun handleUsernameNotFoundException(
        ex: UsernameNotFoundException, request: WebRequest
    ): ResponseEntity<Any> {
        val uri: List<String> = request.getDescription(true).split(";")
        logger.error("username not found exception error: $uri")

        val body: MutableMap<String, Any> = LinkedHashMap()
        body["timestamp"] = LocalDateTime.now()
        body["message"] = ex.message!!
        return ResponseEntity(body, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(InvalidTokenException::class)
    fun handleInvalidTokenException(
        ex: InvalidTokenException, request: WebRequest
    ): ResponseEntity<Any> {
        val uri: List<String> = request.getDescription(true).split(";")
        logger.error("token exception error: $uri")

        val body: MutableMap<String, Any> = LinkedHashMap()
        body["timestamp"] = LocalDateTime.now()
        body["message"] = ex.message
        return ResponseEntity(body, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(LoginException::class)
    fun handleLoginException(
        ex: LoginException, request: WebRequest
    ): ResponseEntity<Any> {
        val uri: List<String> = request.getDescription(true).split(";")
        logger.error("login exception error: $uri")

        val body: MutableMap<String, Any> = LinkedHashMap()
        body["timestamp"] = LocalDateTime.now()
        body["message"] = ex.message!!
        return ResponseEntity(body, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(SessionException::class)
    fun handleSessionException(
        ex: SessionException, request: WebRequest
    ): ResponseEntity<Any> {
        val uri: List<String> = request.getDescription(true).split(";")
        logger.error("session exception error: $uri")

        val body: MutableMap<String, Any> = LinkedHashMap()
        body["timestamp"] = LocalDateTime.now()
        body["message"] = ex.message!!
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

}
