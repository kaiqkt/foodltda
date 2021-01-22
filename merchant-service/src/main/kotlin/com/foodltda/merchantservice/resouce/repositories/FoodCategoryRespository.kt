package com.foodltda.merchantservice.resouce.repositories

import com.foodltda.merchantservice.domain.entities.FoodCategory
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface FoodCategoryRespository: MongoRepository<FoodCategory, String> {
    fun existsByName(name: String): Boolean
    fun findByName(name: String): FoodCategory?
}