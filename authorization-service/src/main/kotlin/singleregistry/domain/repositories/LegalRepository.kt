package singleregistry.domain.repositories

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import singleregistry.domain.entities.Legal

@Repository
interface LegalRepository: MongoRepository<Legal, String> {
    fun existsByEmail(email: String?): Boolean
    fun existsByCnpj(cnpj: String?): Boolean
    fun existsByPhoneCountryCodeAndPhoneAreaCodeAndPhoneNumber(countryCode: String, areaCode: String, number:String): Boolean
}