package com.zed.restaurantservice.domain.entities.restaurant

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class FoodFilter (
        @Id
        var id: String? = null,
        val name: String = "",
        val restaurantId: String?,
)