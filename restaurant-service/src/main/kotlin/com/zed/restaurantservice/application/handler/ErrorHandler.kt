package com.zed.restaurantservice.application.handler

import com.zed.restaurantservice.domain.exceptions.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.LocalDateTime
import java.util.*

@ControllerAdvice
class ErrorHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(ResultBindingException::class)
    fun handleResultBindingException(
        ex: ResultBindingException?, request: WebRequest?
    ): ResponseEntity<Any?>? {
        val uri: List<String>? = request?.getDescription(true)?.split(";")
        logger.error("Invalid result binding exception: ${uri?.get(0)}")

        val body: MutableMap<String, Any> = LinkedHashMap()
        body["timestamp"] = LocalDateTime.now()
        body["message"] = ex?.details()!!
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(DataValidationException::class)
    fun handleDataValidationException(
        ex: DataValidationException?, request: WebRequest?
    ): ResponseEntity<Any?>? {
        val uri: List<String>? = request?.getDescription(true)?.split(";")
        logger.error("Data validation exception: ${uri?.get(0)}")

        val body: MutableMap<String, Any> = LinkedHashMap()
        body["timestamp"] = LocalDateTime.now()
        body["message"] = ex?.details()!!
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(CategoryNotFoundException::class)
    fun handleRestaurantFilterNotFoundException(
        ex: CategoryNotFoundException?, request: WebRequest?
    ): ResponseEntity<Any?>? {
        val uri: List<String>? = request?.getDescription(true)?.split(";")
        logger.error("Restaurant filter not found: ${uri?.get(0)}")

        val body: MutableMap<String, Any> = LinkedHashMap()
        body["timestamp"] = LocalDateTime.now()
        body["message"] = ex?.message!!
        return ResponseEntity(body, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(NotLegalPersonException::class)
    fun handleNotLegalPersonException(
        ex: NotLegalPersonException?, request: WebRequest?
    ): ResponseEntity<Any?>? {
        val uri: List<String>? = request?.getDescription(true)?.split(";")
        logger.error("Not legal person exception: ${uri?.get(0)}")

        val body: MutableMap<String, Any> = LinkedHashMap()
        body["timestamp"] = LocalDateTime.now()
        body["message"] = ex?.message!!
        return ResponseEntity(body, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(RestaurantNotFoundException::class)
    fun handleNRestaurantNotFoundException(
        ex: RestaurantNotFoundException?, request: WebRequest?
    ): ResponseEntity<Any?>? {
        val uri: List<String>? = request?.getDescription(true)?.split(";")
        logger.error("Not legal person exception: ${uri?.get(0)}")

        val body: MutableMap<String, Any> = LinkedHashMap()
        body["timestamp"] = LocalDateTime.now()
        body["message"] = ex?.message!!
        return ResponseEntity(body, HttpStatus.NOT_FOUND)
    }
}