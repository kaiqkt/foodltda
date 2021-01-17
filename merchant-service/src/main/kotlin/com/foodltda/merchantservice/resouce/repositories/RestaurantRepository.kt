package com.foodltda.merchantservice.resouce.repositories

import com.foodltda.merchantservice.domain.entities.Restaurant
import org.springframework.data.mongodb.repository.MongoRepository

interface RestaurantRepository : MongoRepository<Restaurant, String> {
}