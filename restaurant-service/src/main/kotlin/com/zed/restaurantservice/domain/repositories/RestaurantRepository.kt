package com.zed.restaurantservice.domain.repositories

import com.zed.restaurantservice.domain.entities.restaurant.Phone
import com.zed.restaurantservice.domain.entities.restaurant.Restaurant
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface RestaurantRepository : MongoRepository<Restaurant, String> {
    fun existsByCnpj(personId: String?): Boolean
    fun existsByPhone(phone: Phone): Boolean
    fun existsBySlug(slug: String): Boolean
}