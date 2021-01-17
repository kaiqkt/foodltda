package com.foodltda.merchantservice.resouce.repositories

import com.foodltda.merchantservice.domain.entities.Products
import org.springframework.data.mongodb.repository.MongoRepository

interface ProductRepository: MongoRepository<Products, String> {
}