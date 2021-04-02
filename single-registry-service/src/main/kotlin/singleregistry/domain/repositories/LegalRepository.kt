package singleregistry.domain.repositories

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import singleregistry.domain.entities.legal.Legal
import singleregistry.domain.entities.person.Phone

@Repository
interface LegalRepository: MongoRepository<Legal, String> {
    fun existsByCnpj(cnpj: String?): Boolean
    fun findByCnpj(cnpj: String): Legal?
    fun findByPersonPersonId(personId: String?): Legal?
}