package com.foodltda.merchantservice.application.dto


data class DeliveryTime (
        val dayOfWeek: String,
//        dd/MM/yyyy HH:mm:ss
        val openThe: String,
        val closeThe: String
)