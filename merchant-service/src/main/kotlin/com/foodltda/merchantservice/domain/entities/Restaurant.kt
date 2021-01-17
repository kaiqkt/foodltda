package com.foodltda.merchantservice.domain.entities

import com.foodltda.merchantservice.application.dto.Address
import com.foodltda.merchantservice.application.dto.DeliveryTime
import com.foodltda.merchantservice.application.dto.Owner
import com.foodltda.merchantservice.application.dto.request.RestaurantRegistrationDTO
import com.foodltda.merchantservice.domain.entities.enums.Payment
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Restaurant (
        @Id
        val id: String? = null,
        val owner: Owner,
        val image: String,
        val address: Address,
        val telephone: String,
        val deliveryTime: MutableList<DeliveryTime> = mutableListOf(),
        val restaurantLabelList: MutableList<RestaurantLabel> = mutableListOf(),
        val paymentMethods: MutableList<Payment> = mutableListOf(),
        val products: MutableList<Products> = mutableListOf()
) {
        companion object{
                fun fromDocument(restaurantDTO: RestaurantRegistrationDTO) =
                        Restaurant(
                                owner = restaurantDTO.owner,
                                image = restaurantDTO.image,
                                address = restaurantDTO.address,
                                telephone = restaurantDTO.telephone,
                                deliveryTime = restaurantDTO.deliveryTime,
                                restaurantLabelList = restaurantDTO.restaurantLabelList,
                                paymentMethods = restaurantDTO.paymentMethods
                        )
        }
}