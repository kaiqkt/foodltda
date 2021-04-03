package com.zed.restaurantservice.domain.repositories

import com.zed.restaurantservice.domain.entities.restaurant.Products
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductsRepository: MongoRepository<Products, String> {
}