package com.orderservice.paymentservice.domain.entities

import java.math.BigDecimal

data class Order(
    val restaurantId: String,
    val personId: String,
    val payment: Payment,
    val address: Address,
    val products: List<Product>,
    val totalPrice: BigDecimal
)