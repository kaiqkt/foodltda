package com.foodltda.merchantservice.domain.repositories

import com.foodltda.merchantservice.domain.entities.LegalPerson
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface LegalPersonRepository: MongoRepository<LegalPerson, String> {
    override fun findById(id: String): Optional<LegalPerson>
    fun existsByTelephone(telephone: String): Boolean
    fun existsByEmail(email: String): Boolean
    fun existsByCnpj(cnpj: String): Boolean
}