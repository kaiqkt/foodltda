package com.zed.restaurantservice.domain.entities.filter

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Category (
        @Id
        var _id: String? = null,
        val name: String,
        val image: String,
)