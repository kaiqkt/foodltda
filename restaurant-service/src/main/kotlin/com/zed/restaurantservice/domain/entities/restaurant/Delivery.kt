package com.zed.restaurantservice.domain.entities.restaurant

data class Delivery(
    val monday: DeliveryTime,
    val tuesday: DeliveryTime,
    val wednesday: DeliveryTime,
    val thursday: DeliveryTime,
    val friday: DeliveryTime,
    val saturday: DeliveryTime,
    val sunday: DeliveryTime,
)