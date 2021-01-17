package com.foodltda.merchantservice.resouce.repositories

import com.foodltda.merchantservice.domain.entities.LoginUser
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository


@Repository
interface LoginPersonRepository: MongoRepository<LoginUser, String> {
}