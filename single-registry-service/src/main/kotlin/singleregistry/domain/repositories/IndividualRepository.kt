package singleregistry.domain.repositories

import org.springframework.data.mongodb.repository.MongoRepository
import singleregistry.domain.entities.individual.Individual

interface IndividualRepository: MongoRepository<Individual, String> {
    fun existsByCpf(cpf: String?): Boolean
    fun findByCpf(cpf: String): Individual?
    fun findByPersonPersonId(personId: String): Individual?
}