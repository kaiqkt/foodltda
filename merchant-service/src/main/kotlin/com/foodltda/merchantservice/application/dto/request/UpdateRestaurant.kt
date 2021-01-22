package com.foodltda.merchantservice.application.dto.request

import com.foodltda.merchantservice.application.dto.Address
import com.foodltda.merchantservice.application.dto.DeliveryTime
import com.foodltda.merchantservice.domain.entities.enums.Payment

data class UpdateRestaurant (
    val name: String? = null,
    val image: String? = null,
    val address: Address? = null,
    val telephone: String? = null,
    val deliveryTime: MutableList<DeliveryTime>? = null,
    val foodCategory: MutableList<String>? = null,
    val paymentMethods: MutableList<Payment>? = null
)