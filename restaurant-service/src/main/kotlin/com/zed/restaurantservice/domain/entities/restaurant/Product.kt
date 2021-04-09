package com.zed.restaurantservice.domain.entities.restaurant

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

@Document
data class Product (
        @Id
        val _id: String? = null,
        var slug: String? = null,
        val name: String?,
        val image: String?,
        val price: BigDecimal?,
        val description: String?,
        val foodCategory: String?,
        val restaurantId: String? = null
)