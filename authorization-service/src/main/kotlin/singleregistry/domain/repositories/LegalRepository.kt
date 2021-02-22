package singleregistry.domain.repositories

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import singleregistry.domain.entities.legal.Legal

@Repository
interface LegalRepository: MongoRepository<Legal, String> {
    fun existsByPersonEmail(email: String?): Boolean
    fun existsByCnpj(cnpj: String?): Boolean
    fun existsByPersonPhoneCountryCodeAndPersonPhoneAreaCodeAndPersonPhoneNumber(countryCode: String?, areaCode: String?, number:String?): Boolean
}