package com.foodltda.merchantservice.domain.entities

import java.time.DayOfWeek


data class DeliveryTime (
        val dayOfWeek: DayOfWeek,
//        dd/MM/yyyy HH:mm:ss
        val openThe: String,
        val closeThe: String
)