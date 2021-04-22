package com.orderservice.orderservice.application.controllers

import com.orderservice.orderservice.application.requests.OrderRequest
import com.orderservice.orderservice.application.requests.toDomain
import com.orderservice.orderservice.domain.constants.START_TOPIC
import com.orderservice.orderservice.resources.kafka.producer.OrderEventProducer
import com.orderservice.orderservice.domain.events.OrderEvent
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class StartSagaController(private val eventProducer: OrderEventProducer) {

    @PostMapping("/order")
    fun execute(
        @RequestHeader("Authorization") auth_token: String,
        @RequestBody request: OrderRequest
    ): ResponseEntity<OrderEvent> {
        //decrypted
        val personId = auth_token
        val event = OrderEvent.create(request.toDomain(personId))
        eventProducer.send(event, START_TOPIC)

        return ResponseEntity.status(HttpStatus.CREATED).body(event)
    }
}