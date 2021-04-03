package com.zed.restaurantservice.domain.entities.restaurant

import com.zed.restaurantservice.domain.entities.filter.Payment
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Restaurant(
    @Id
    val _id: String? = null,
    val personId: String? = null,
    val cnpj: String? = null,
    val slug: String? = null,
    val name: String,
    val image: String,
    val address: Address,
    val phone: Phone,
    val delivery: Delivery?,
    val restaurantFilter: String,
    val payment: MutableList<Payment>,
    val products: MutableList<Products> = mutableListOf()
)
