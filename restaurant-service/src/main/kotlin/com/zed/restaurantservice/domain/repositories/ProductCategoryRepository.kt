package com.zed.restaurantservice.domain.repositories

import com.zed.restaurantservice.domain.entities.restaurant.ProductCategory
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductCategoryRepository: MongoRepository<ProductCategory, String> {
    fun findByName(name: String?): ProductCategory?
    fun findAllByRestaurantId(id: String?): List<ProductCategory>
}