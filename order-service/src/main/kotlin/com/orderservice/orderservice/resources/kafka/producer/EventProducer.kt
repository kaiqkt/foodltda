package com.orderservice.orderservice.resources.kafka.producer

import com.orderservice.orderservice.domain.events.Event

interface EventProducer {
    fun send(event: Event, topic: String)
}