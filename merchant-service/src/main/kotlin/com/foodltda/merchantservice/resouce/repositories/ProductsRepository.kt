package com.foodltda.merchantservice.resouce.repositories

import com.foodltda.merchantservice.domain.entities.Products
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductsRepository: MongoRepository<Products, String> {
    fun existsBySlug(slug: String): Boolean
    fun findBySlug(slug: String): Products?
    fun findByTagName(name: String, restaurantId: String): Products?
}