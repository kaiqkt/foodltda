package com.foodltda.merchantservice.resouce.repositories

import com.foodltda.merchantservice.domain.entities.LegalPerson
import org.springframework.data.mongodb.repository.MongoRepository

interface LegalPersonRepository: MongoRepository<LegalPerson, String> {
}