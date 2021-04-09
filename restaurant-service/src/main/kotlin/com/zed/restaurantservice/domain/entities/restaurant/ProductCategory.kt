package com.zed.restaurantservice.domain.entities.restaurant

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class ProductCategory (
        @Id
        var _id: String? = null,
        val name: String = "",
        val restaurantId: String? = null,
)