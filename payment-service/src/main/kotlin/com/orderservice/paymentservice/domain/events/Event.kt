package com.orderservice.paymentservice.domain.events

import com.orderservice.paymentservice.domain.entities.Order

abstract class Event {
    abstract val eventId: String

    companion object {
        fun createEvent(order: Any): Event =
            OrderEvent.create(
                order = order as Order
            )
    }
}
