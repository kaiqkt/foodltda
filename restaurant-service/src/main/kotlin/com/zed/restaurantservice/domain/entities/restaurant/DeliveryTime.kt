package com.zed.restaurantservice.domain.entities.restaurant

import java.time.DayOfWeek


data class DeliveryTime (
//       HH:mm:ss
        val dayOfWeek: DayOfWeek,
        val openAt: String,
        val closeAt: String
)