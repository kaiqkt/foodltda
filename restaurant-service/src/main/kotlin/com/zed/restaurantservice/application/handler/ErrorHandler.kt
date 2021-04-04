package com.zed.restaurantservice.application.handler

import com.zed.restaurantservice.domain.exceptions.RestaurantFilterNotFoundException
import com.zed.restaurantservice.domain.exceptions.ResultBindingException
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
        ex: ResultBindingException?, request: WebRequest?): ResponseEntity<Any?>? {
        val uri: List<String>? = request?.getDescription(true)?.split(";")
        logger.error("Invalid result binding: ${uri?.get(0)}")

        val body: MutableMap<String, Any> = LinkedHashMap()
        body["timestamp"] = LocalDateTime.now()
        body["message"] = ex?.details()!!
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(RestaurantFilterNotFoundException::class)
    fun handleRestaurantFilterNotFoundException(
        ex: RestaurantFilterNotFoundException?, request: WebRequest?): ResponseEntity<Any?>? {
        val uri: List<String>? = request?.getDescription(true)?.split(";")
        logger.error("Restaurant filter not found: ${uri?.get(0)}")

        val body: MutableMap<String, Any> = LinkedHashMap()
        body["timestamp"] = LocalDateTime.now()
        body["message"] = ex?.message!!
        return ResponseEntity(body, HttpStatus.NOT_FOUND)
    }
}