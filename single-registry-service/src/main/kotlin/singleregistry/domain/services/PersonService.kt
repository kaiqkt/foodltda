package singleregistry.domain.services

import org.springframework.stereotype.Service
import singleregistry.application.dto.PersonResponse
import singleregistry.domain.entities.person.PersonType
import singleregistry.domain.exceptions.PersonNotFoundException
import singleregistry.domain.repositories.IndividualRepository
import singleregistry.domain.repositories.LegalRepository
import singleregistry.domain.repositories.PersonRepository
import java.lang.IllegalArgumentException

@Service
class PersonService(
    private val personRepository: PersonRepository,
    private val legalPersonRepository: LegalRepository,
    private val individualRepository: IndividualRepository
) {

    fun findByPersonId(personId: String): PersonResponse {
        val person = personRepository.findByPersonId(personId) ?: throw PersonNotFoundException("Person not found by id: $personId")

        return when(person.type) {
             PersonType.PF -> PersonResponse(individual = individualRepository.findByPersonPersonId(personId))
             PersonType.PJ -> PersonResponse(legal = legalPersonRepository.findByPersonPersonId(personId))
            else -> throw IllegalArgumentException("Invalid person type: ${person.type}")
        }
    }
}