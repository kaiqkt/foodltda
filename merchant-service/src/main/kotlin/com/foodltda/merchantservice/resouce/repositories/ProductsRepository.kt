package com.foodltda.merchantservice.resouce.repositories

import com.foodltda.merchantservice.domain.entities.Products
import org.springframework.data.mongodb.repository.MongoRepository

interface ProductsRepository: MongoRepository<Products, String> {
    fun existsBySlug(slug: String): Boolean
}