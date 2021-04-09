package com.zed.restaurantservice.domain.repositories

import com.zed.restaurantservice.domain.entities.restaurant.Product
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductsRepository: MongoRepository<Product, String> {
    fun existsBySlug(slug: String?): Boolean
    fun findBySlugAndRestaurantId(slug: String?, restaurantId: String?): Product?
    fun findAllByFoodCategoryAndRestaurantId(category: String?, restaurantId: String?): List<Product>
    fun findAllByRestaurantId(restaurantId: String?): List<Product>
}