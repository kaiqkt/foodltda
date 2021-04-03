package com.foodltda.merchantservice.application.dto.response

import com.foodltda.merchantservice.domain.entities.Restaurant

data class RestaurantDTO (
        val restaurant: Restaurant,
        val openingHours: OpeningHours,
)