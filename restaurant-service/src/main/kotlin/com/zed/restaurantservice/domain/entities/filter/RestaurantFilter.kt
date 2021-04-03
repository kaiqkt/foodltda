package com.zed.restaurantservice.domain.entities.filter

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class RestaurantFilter (
        @Id
        var id: String? = null,
        val name: String = "",
)