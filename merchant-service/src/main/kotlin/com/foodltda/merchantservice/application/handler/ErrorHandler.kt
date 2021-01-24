package com.foodltda.merchantservice.application.handler

import com.foodltda.merchantservice.domain.exceptions.*
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
        return ResponseEntity(body, HttpStatus.UNPROCESSABLE_ENTITY)
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun handleUserNotFoundException(
            ex: NoSuchElementException?, request: WebRequest?): ResponseEntity<Any?>? {
        val uri: List<String>? = request?.getDescription(true)?.split(";")
        logger.error("Legal person not found: ${uri?.get(0)}")

        val body: MutableMap<String, Any> = LinkedHashMap()
        body["timestamp"] = LocalDateTime.now()
        body["message"] = ex?.message!!
        return ResponseEntity(body, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(OwnerException::class)
    fun handleUserOwnerException(
            ex: OwnerException?, request: WebRequest?): ResponseEntity<Any?>? {
        val uri: List<String>? = request?.getDescription(true)?.split(";")
        logger.error("Person id not related to the restaurant: ${uri?.get(0)}")

        val body: MutableMap<String, Any> = LinkedHashMap()
        body["timestamp"] = LocalDateTime.now()
        body["message"] = ex?.message!!
        return ResponseEntity(body, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(TagNotFoundException::class)
    fun handleTagNotFoundException(
            ex: TagNotFoundException?, request: WebRequest?): ResponseEntity<Any?>? {
        val uri: List<String>? = request?.getDescription(true)?.split(";")
        logger.error("Tag not found: ${uri?.get(0)}")

        val body: MutableMap<String, Any> = LinkedHashMap()
        body["timestamp"] = LocalDateTime.now()
        body["message"] = ex?.message!!
        return ResponseEntity(body, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(PaymentNotFoundException::class)
    fun handlePaymentNotFoundException(
            ex: PaymentNotFoundException?, request: WebRequest?): ResponseEntity<Any?>? {
        val uri: List<String>? = request?.getDescription(true)?.split(";")
        logger.error("Tag not found: ${uri?.get(0)}")

        val body: MutableMap<String, Any> = LinkedHashMap()
        body["timestamp"] = LocalDateTime.now()
        body["message"] = ex?.message!!
        return ResponseEntity(body, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(ProductNotFoundException::class)
    fun handleProductNotFoundException(
            ex: ProductNotFoundException?, request: WebRequest?): ResponseEntity<Any?>? {
        val uri: List<String>? = request?.getDescription(true)?.split(";")
        logger.error("Product not found: ${uri?.get(0)}")

        val body: MutableMap<String, Any> = LinkedHashMap()
        body["timestamp"] = LocalDateTime.now()
        body["message"] = ex?.message!!
        return ResponseEntity(body, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(RestaurantNotFoundException::class)
    fun handleRestaurantNotFoundException(
            ex: RestaurantNotFoundException?, request: WebRequest?): ResponseEntity<Any?>? {
        val uri: List<String>? = request?.getDescription(true)?.split(";")
        logger.error("Product not found: ${uri?.get(0)}")

        val body: MutableMap<String, Any> = LinkedHashMap()
        body["timestamp"] = LocalDateTime.now()
        body["message"] = ex?.message!!
        return ResponseEntity(body, HttpStatus.NOT_FOUND)
    }
}