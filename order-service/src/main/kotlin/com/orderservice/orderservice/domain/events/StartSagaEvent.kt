package com.orderservice.orderservice.domain.events

import com.orderservice.orderservice.domain.entities.Order
import io.azam.ulidj.ULID

class StartSagaEvent(
    override val eventId: String,
    private val order: Order
) : Event(order) {

    companion object {
        fun create(order: Order): StartSagaEvent {
            return StartSagaEvent(
                eventId = ULID.random(),
                order = order
            )
        }
    }
}
