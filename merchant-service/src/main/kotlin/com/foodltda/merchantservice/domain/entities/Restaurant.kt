package com.foodltda.merchantservice.domain.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import com.foodltda.merchantservice.application.dto.request.RestaurantRegistrationDTO
import com.foodltda.merchantservice.domain.entities.enums.Payment
import com.github.slugify.Slugify
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Restaurant (
        @Id
        val id: String? = null,
        val slug: String? = null,
        val name: String,
        @JsonIgnore
        val legalPersonId: String?,
        val image: String,
        val address: Address?,
        val telephone: String? = null,
        @JsonIgnore
        val deliveryTime: MutableList<DeliveryTime> = mutableListOf(),
        val restaurantCategory: RestaurantCategory? = null,
        val paymentMethods: MutableList<Payment> = mutableListOf(),
        val products: MutableList<String?> = mutableListOf()
) {
        companion object{
                fun fromDocument(legalPersonId: String, restaurantDTO: RestaurantRegistrationDTO, tag: RestaurantCategory?) =
                        Restaurant(
                                legalPersonId = legalPersonId,
                                slug = Slugify().slugify(restaurantDTO.name),
                                name = restaurantDTO.name,
                                image = restaurantDTO.image,
                                address = restaurantDTO.address,
                                telephone = restaurantDTO.telephone,
                                deliveryTime = restaurantDTO.deliveryTime,
                                restaurantCategory = tag,
                                paymentMethods = restaurantDTO.paymentMethods
                        )
        }
}
