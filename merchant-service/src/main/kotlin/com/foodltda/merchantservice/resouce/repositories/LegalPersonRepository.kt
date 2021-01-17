package com.foodltda.merchantservice.resouce.repositories

import com.foodltda.merchantservice.domain.entities.LegalPerson
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface LegalPersonRepository: MongoRepository<LegalPerson, String> {
    fun findLegalPersonByCnpj(cnpj: String): LegalPerson
    fun existsByEmail(email: String): Boolean
    fun existsByCnpj(cnpj: String): Boolean
}