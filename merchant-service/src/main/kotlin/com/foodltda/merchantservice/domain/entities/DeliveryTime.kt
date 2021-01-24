package com.foodltda.merchantservice.domain.entities


data class DeliveryTime (
        val dayOfWeek: String,
//        dd/MM/yyyy HH:mm:ss
        val openThe: String,
        val closeThe: String
)