package com.orderservice.paymentservice.domain.services

import com.orderservice.paymentservice.domain.constants.PAYMENT_FAILURE_TOPIC
import com.orderservice.paymentservice.domain.constants.PAYMENT_SUCCESS_TOPIC
import com.orderservice.paymentservice.domain.entities.Order
import com.orderservice.paymentservice.domain.entities.Payment
import com.orderservice.paymentservice.domain.events.Event
import com.orderservice.paymentservice.domain.events.Outcome
import com.orderservice.paymentservice.domain.events.getOutcome
import com.orderservice.paymentservice.resources.kafka.producer.EventProducer
import com.orderservice.paymentservice.resources.restaurantservice.gateway.RestaurantServiceImpl
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class PaymentService(
    private val eventProducer: EventProducer,
    private val restaurantServiceImpl: RestaurantServiceImpl
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun execute(order: Order) {
        val result = result(order.restaurantId, order.payment)
        val outcome = getOutcome(result)

        logger.info("When trying to pay a product for restaurant ${order.restaurantId}, for customer ${order.personId} received outcome ${outcome.name}")

        eventProducer.send(
            Event.createEvent(
                order
            ),
            getTopicFromOutcome(outcome)
        )
    }

    private fun result(restaurantId: String, payment: Payment): Boolean {
        return if (restaurantServiceImpl.containsPayment(restaurantId, payment)) {
            return generateResult(rand())
        } else {
            false
        }
    }

    private fun generateResult(number: Int): Boolean {
        require(number in 0..4)
        return number != 4
    }

    private fun rand(start: Int = 0, end: Int = 4): Int {
        require(!(start > end || end - start + 1 > Int.MAX_VALUE)) { "Illegal Argument" }
        return Random(System.nanoTime()).nextInt(end - start + 1) + start
    }

    private fun getTopicFromOutcome(outcome: Outcome) = when (outcome) {
        Outcome.SUCCESS -> PAYMENT_SUCCESS_TOPIC
        else -> PAYMENT_FAILURE_TOPIC
    }
}
