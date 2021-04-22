package com.orderservice.orderservice.application.dto

import com.orderservice.orderservice.domain.entities.Order

data class OrderDto(
    val restaurantId: String,
    val products: List<String>
)

fun OrderDto.toDomain() = Order(
    restaurantId = this.restaurantId,
    products = this.products
)