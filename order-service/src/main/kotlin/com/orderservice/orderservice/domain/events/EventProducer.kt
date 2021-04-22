package com.orderservice.orderservice.domain.events

interface EventProducer {
    fun send(event: Event, topic: String)
}