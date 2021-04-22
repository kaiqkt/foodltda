package com.orderservice.orderservice.application.requests

import com.orderservice.orderservice.domain.entities.Address
import com.orderservice.orderservice.domain.entities.Order
import com.orderservice.orderservice.domain.entities.Payment
import com.orderservice.orderservice.domain.entities.Product
import java.math.BigDecimal

data class OrderRequest(
    val restaurantId: String,
    val payment: Payment,
    val address: Address,
    val products: List<Product>,
    val totalPrice: BigDecimal
)

fun OrderRequest.toDomain(personId: String) = Order(
    restaurantId = this.restaurantId,
    personId = personId,
    payment = this.payment,
    address = this.address,
    products = this.products,
    totalPrice = this.totalPrice
)