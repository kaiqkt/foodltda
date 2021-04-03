package com.zed.restaurantservice.domain.entities.restaurant


data class DeliveryTime (
//       HH:mm:ss
        val openThe: String,
        val closeThe: String,
        val closed: Boolean = false
)