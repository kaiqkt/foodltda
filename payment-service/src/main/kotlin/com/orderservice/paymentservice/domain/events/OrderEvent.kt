package com.orderservice.paymentservice.domain.events

import com.orderservice.paymentservice.domain.entities.Order
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

enum class Outcome {
    SUCCESS, FAILURE
}

fun getOutcome(wasSuccess: Boolean): Outcome = when (wasSuccess) {
    true -> Outcome.SUCCESS
    else -> Outcome.FAILURE
}