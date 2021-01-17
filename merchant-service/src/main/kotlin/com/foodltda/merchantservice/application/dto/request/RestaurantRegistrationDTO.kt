package com.foodltda.merchantservice.application.dto.request

import com.foodltda.merchantservice.application.dto.Address
import com.foodltda.merchantservice.application.dto.DeliveryTime
import com.foodltda.merchantservice.application.dto.Owner
import com.foodltda.merchantservice.domain.entities.RestaurantLabel
import com.foodltda.merchantservice.domain.entities.enums.Payment

data class RestaurantRegistrationDTO (
        val owner: Owner,
        val image: String,
        val address: Address,
        val telephone: String,
        val deliveryTime: MutableList<DeliveryTime> = mutableListOf(),
        val restaurantLabelList: MutableList<RestaurantLabel> = mutableListOf(),
        val paymentMethods: MutableList<Payment> = mutableListOf()
)