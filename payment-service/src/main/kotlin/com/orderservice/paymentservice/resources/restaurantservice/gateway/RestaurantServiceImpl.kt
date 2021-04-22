package com.orderservice.paymentservice.resources.restaurantservice.gateway

import com.orderservice.paymentservice.domain.entities.Payment
import com.orderservice.paymentservice.domain.exceptions.CommunicationException
import com.orderservice.paymentservice.resources.restaurantservice.client.RestaurantClient
import feign.FeignException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class RestaurantServiceImpl(
    private val restaurantClient: RestaurantClient,
    @Value("\${restaurant_service_token}") private var token: String
) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun containsPayment(restaurantId: String, payment: Payment): Boolean {
        var contains = false

        try {
            restaurantClient.findByRestaurantId(restaurantId, token).let {
                if (it.body?.payments?.contains(payment) == true) {
                    contains = true
                }
            }

        } catch (ex: FeignException) {
            when (ex.status()) {
                400 -> log.error(ex.message)
                403 -> log.error(ex.message)
                else -> throw CommunicationException("Error when find restaurant $restaurantId")
            }
        }

        return contains
    }
}