package com.orderservice.orderservice.domain.events

import com.orderservice.orderservice.domain.entities.Order
import io.azam.ulidj.ULID

class OrderEvent(
    override val eventId: String,
    val order: Order
) : Event() {

    companion object {
        fun create(order: Order): OrderEvent {
            return OrderEvent(
                eventId = ULID.random(),
                order = order
            )
        }
    }
}
