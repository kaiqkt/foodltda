package singleregistry.domain.repositories

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import singleregistry.domain.entities.legal.Legal
import singleregistry.domain.entities.person.Phone

@Repository
interface LegalRepository: MongoRepository<Legal, String> {
    fun existsByPersonEmail(email: String?): Boolean
    fun existsByCnpj(cnpj: String?): Boolean
    fun existsByPersonPhone(phone: Phone?): Boolean
    fun findByCnpj(cnpj: String): Legal?
}