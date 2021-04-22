package com.orderservice.paymentservice.resources.kafka

import com.orderservice.paymentservice.domain.events.Event

interface EventProducer {
    fun send(event: Event, topic: String)
}