package com.zed.restaurantservice.domain.repositories

import com.zed.restaurantservice.domain.entities.restaurant.FoodFilter
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface FoodFilterRepository: MongoRepository<FoodFilter, String> {

}