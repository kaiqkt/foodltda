package com.foodltda.merchantservice.resouce.repositories

import org.springframework.data.mongodb.repository.MongoRepository

interface FoodCategory: MongoRepository<FoodCategory, String> {
}