package com.foodltda.merchantservice.domain.repositories

import com.foodltda.merchantservice.domain.entities.RestaurantCategory
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface RestaurantCategoryRepository: MongoRepository<RestaurantCategory, String> {
    fun findByName(name: String): RestaurantCategory?
}