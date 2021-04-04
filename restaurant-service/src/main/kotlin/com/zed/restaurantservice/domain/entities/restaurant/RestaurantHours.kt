package com.zed.restaurantservice.domain.entities.restaurant

data class RestaurantHours(
    val restaurant: Restaurant,
    val openAt: String,
    val closeAt: String,
    val closed: Boolean = true
)