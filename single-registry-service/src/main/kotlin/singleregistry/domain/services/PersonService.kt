package singleregistry.domain.services

import org.springframework.stereotype.Service
import singleregistry.domain.entities.person.Person
import singleregistry.domain.exceptions.PersonNotFoundException
import singleregistry.domain.repositories.PersonRepository

@Service
class PersonService(private val personRepository: PersonRepository) {

    fun findByPersonId(personId: String) =
        personRepository.findByPersonId(personId) ?: throw PersonNotFoundException("Person not found by id: $personId")
}