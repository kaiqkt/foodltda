package com.foodltda.merchantservice.resouce.repositories

import com.foodltda.merchantservice.domain.entities.Tag
import org.springframework.data.mongodb.repository.MongoRepository

interface TagRepository: MongoRepository<Tag, String> {
    fun findByName(name: String?): Tag?
    fun findByRestaurantId(id: String): Tag?
    fun findAllByRestaurantId(id: String): List<Tag>?
}