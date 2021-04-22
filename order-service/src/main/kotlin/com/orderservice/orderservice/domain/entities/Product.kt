package com.orderservice.orderservice.domain.entities

import java.math.BigDecimal

data class Product (
        val _id: String? = null,
        val name: String?,
        val price: BigDecimal?,
        val foodCategory: String?,
)