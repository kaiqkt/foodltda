package com.orderservice.paymentservice.domain.services

import com.orderservice.paymentservice.domain.constants.PAYMENT_FAILURE_TOPIC
import com.orderservice.paymentservice.domain.constants.PAYMENT_SUCCESS_TOPIC
import com.orderservice.paymentservice.domain.entities.Order
import com.orderservice.paymentservice.domain.events.Event
import com.orderservice.paymentservice.domain.events.Outcome
import com.orderservice.paymentservice.domain.events.getOutcome
import com.orderservice.paymentservice.resources.gateway.PaymentClient
import com.orderservice.paymentservice.resources.kafka.EventProducer
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class PaymentService(
    private val client: PaymentClient,
    private val eventProducer: EventProducer
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun execute(order: Order) {
        val result = client.result()
        val outcome = getOutcome(result)

        logger.info("When trying to pay a product for restaurant ${order.restaurantId}, for customer ${order.personId} received outcome ${outcome.name}")

        eventProducer.send(
            Event.createEvent(
                order
            ),
            getTopicFromOutcome(outcome)
        )
    }

    private fun getTopicFromOutcome(outcome: Outcome) = when (outcome) {
        Outcome.SUCCESS -> PAYMENT_SUCCESS_TOPIC
        else -> PAYMENT_FAILURE_TOPIC
    }
}
