package com.zed.restaurantservice.application.dto

import com.zed.restaurantservice.domain.entities.restaurant.Restaurant

data class RestaurantsResponse(
    val restaurant : Restaurant,
    val closed: Boolean = true
)
