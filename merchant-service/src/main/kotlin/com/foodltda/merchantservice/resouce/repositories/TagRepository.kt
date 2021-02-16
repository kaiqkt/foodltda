package com.foodltda.merchantservice.resouce.repositories

import com.foodltda.merchantservice.domain.entities.Tag
import org.springframework.data.mongodb.repository.MongoRepository

interface TagRepository: MongoRepository<Tag, String> {
    fun findByNameAndRestaurantId(name: String?, restaurantId: String?): Tag?
    fun deleteByName(name: String)
}