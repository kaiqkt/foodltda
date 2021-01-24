package com.foodltda.merchantservice.domain.entities

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
        val owner: Owner,
        val image: String,
        val address: Address?,
        val telephone: String? = null,
        val deliveryTime: MutableList<DeliveryTime> = mutableListOf(),
        val foodCategory: MutableList<FoodCategory>? = mutableListOf(),
        val paymentMethods: MutableList<Payment> = mutableListOf(),
        val products: MutableList<Products> = mutableListOf()
) {
        companion object{
                fun fromDocument(owner: Owner, restaurantDTO: RestaurantRegistrationDTO, tagList: MutableList<FoodCategory>?) =
                        Restaurant(
                                owner = owner,
                                slug = Slugify().slugify(restaurantDTO.name),
                                name = restaurantDTO.name,
                                image = restaurantDTO.image,
                                address = restaurantDTO.address,
                                telephone = restaurantDTO.telephone,
                                deliveryTime = restaurantDTO.deliveryTime,
                                foodCategory = tagList,
                                paymentMethods = restaurantDTO.paymentMethods
                        )
        }
}
