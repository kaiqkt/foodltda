package com.zed.restaurantservice.domain.entities.restaurant

data class Phone(
    val countryCode: String,
    val areaCode: String,
    val number: String
)