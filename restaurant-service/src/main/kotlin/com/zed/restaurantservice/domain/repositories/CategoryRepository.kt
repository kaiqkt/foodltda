package com.zed.restaurantservice.domain.repositories

import com.zed.restaurantservice.domain.entities.filter.Category
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository: MongoRepository<Category, String> {
    fun findByName(name: String): Category?
    fun existsByName(name: String): Boolean
}