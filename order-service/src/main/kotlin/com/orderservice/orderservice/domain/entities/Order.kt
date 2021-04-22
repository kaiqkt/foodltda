package com.orderservice.orderservice.domain.entities

data class Order(
    val restaurantId: String,
    val products: List<String>
)