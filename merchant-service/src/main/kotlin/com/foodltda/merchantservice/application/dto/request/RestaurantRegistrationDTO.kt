package com.foodltda.merchantservice.application.dto.request

import com.foodltda.merchantservice.domain.entities.Address
import com.foodltda.merchantservice.domain.entities.DeliveryTime
import com.foodltda.merchantservice.domain.entities.enums.Payment
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class RestaurantRegistrationDTO (
        @get:NotEmpty(message = "Restaurant name cannot be empty")
        val name: String = "",
        @get:NotEmpty(message = "Image cannot be empty")
        val image: String = "",
        @get:NotNull(message = "Address cannot be null")
        val address: Address?,
        @get:NotEmpty(message = "Telephone cannot be empty")
        val telephone: String = "",
        @get:NotEmpty(message = "Delivery time cannot be empty")
        val deliveryTime: MutableList<DeliveryTime> = mutableListOf(),
        @get:NotEmpty(message = "Food category cannot be empty")
        val foodCategory: MutableList<String>? = mutableListOf(),
        @get:NotEmpty(message = "Payment methods cannot be empty")
        val paymentMethods: MutableList<Payment> = mutableListOf()
)