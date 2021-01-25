package com.foodltda.merchantservice.application.dto.response

import com.foodltda.merchantservice.domain.entities.DeliveryTime

data class RestaurantsDTO (
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