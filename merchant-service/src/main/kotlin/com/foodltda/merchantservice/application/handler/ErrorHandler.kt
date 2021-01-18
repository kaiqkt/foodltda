package com.foodltda.merchantservice.application.handler

import com.foodltda.merchantservice.domain.exceptions.ResultBindingException
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
        val body: MutableMap<String, Any> = LinkedHashMap()
        body["timestamp"] = LocalDateTime.now()
        body["message"] = ex?.details()!!
        return ResponseEntity(body, HttpStatus.UNPROCESSABLE_ENTITY)
    }
}