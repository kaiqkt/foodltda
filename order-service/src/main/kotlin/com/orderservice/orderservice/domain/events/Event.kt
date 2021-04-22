package com.orderservice.orderservice.domain.events

import com.orderservice.orderservice.domain.entities.Order

abstract class Event(val content: Any) {
    abstract val eventId: String

    companion object {
        fun createEvent(order: Any): Event =
            StartSagaEvent.create(
                order = order as Order
            )
    }
}
