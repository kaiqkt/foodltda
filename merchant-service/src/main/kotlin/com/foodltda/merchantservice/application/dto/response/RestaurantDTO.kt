package com.foodltda.merchantservice.application.dto.response

import com.foodltda.merchantservice.domain.entities.DeliveryTime
import com.foodltda.merchantservice.domain.entities.Restaurant

data class RestaurantDTO (
        val id: String?,
        val name: String,
        val slug: String?,
        val image: String?,
        val time: DeliveryTime?,
        val openingHours: OpeningHours,
)

enum class OpeningHours{
    CLOSED,OPEN
}