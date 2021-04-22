package com.orderservice.paymentservice.resources.restaurantservice.entities

import com.orderservice.paymentservice.domain.entities.Payment

data class Response(
    val _id: String,
    val payments: MutableList<Payment>
)