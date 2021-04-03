package com.zed.restaurantservice.domain.repositories

import com.zed.restaurantservice.domain.entities.filter.RestaurantFilter
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface RestaurantFilterRepository: MongoRepository<RestaurantFilter, String> {
    fun findByName(name: String): RestaurantFilter?
}