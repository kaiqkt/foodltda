package com.zed.restaurantservice.domain.entities.restaurant

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

@Document
data class Products (
        @Id
        val id: String? = null,
        var slug: String?,
        val name: String?,
        val image: String?,
        val price: BigDecimal?,
        val description: String?,
        val tag: FoodFilter?,
        val restaurantId: String
)