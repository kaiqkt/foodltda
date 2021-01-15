package com.foodltda.merchantservice.application.handler

import com.foodltda.merchantservice.domain.exceptions.CityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.LocalDateTime
import java.util.*


@ControllerAdvice
class RestExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(CityNotFoundException::class)
    fun handleCityNotFoundException(
            ex: CityNotFoundException?, request: WebRequest?): ResponseEntity<Any?>? {
        val body: MutableMap<String, Any> = LinkedHashMap()
        body["timestamp"] = LocalDateTime.now()
        body["message"] = "City not found"
        return ResponseEntity(body, HttpStatus.NOT_FOUND)
    }

}