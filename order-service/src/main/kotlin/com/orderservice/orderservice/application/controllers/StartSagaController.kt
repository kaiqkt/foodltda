package com.orderservice.orderservice.application.controllers

import com.orderservice.orderservice.application.dto.OrderDto
import com.orderservice.orderservice.application.dto.toDomain
import com.orderservice.orderservice.domain.constants.TOPIC_NAME
import com.orderservice.orderservice.domain.events.OrderEventProducer
import com.orderservice.orderservice.domain.events.StartSagaEvent
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class StartSagaController(private val eventProducer: OrderEventProducer) {

    @PostMapping("/order")
    fun execute(@RequestBody dto: OrderDto): ResponseEntity<StartSagaEvent> {
        val event = StartSagaEvent.create(dto.toDomain())
        eventProducer.send(event, TOPIC_NAME)
        return ResponseEntity.status(HttpStatus.CREATED).body(event)
    }
}