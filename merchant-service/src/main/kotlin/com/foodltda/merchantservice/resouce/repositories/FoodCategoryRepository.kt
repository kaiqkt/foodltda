package com.foodltda.merchantservice.resouce.repositories

import com.foodltda.merchantservice.domain.entities.FoodCategory
import com.foodltda.merchantservice.domain.entities.Tag
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface FoodCategoryRepository: MongoRepository<FoodCategory, String> {
    fun existsByName(name: String): Boolean
    fun findByName(name: String): FoodCategory?
}