package com.foodltda.merchantservice.domain.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class RestaurantLabel (
        val name: String = "",
        @Id
        @JsonIgnore
        var id: String? = null
)