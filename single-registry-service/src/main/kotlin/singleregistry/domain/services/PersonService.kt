package singleregistry.domain.services

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import singleregistry.application.dto.IndividualPersonResponse
import singleregistry.application.dto.LegalPersonResponse
import singleregistry.domain.entities.person.PersonType
import singleregistry.domain.repositories.IndividualRepository
import singleregistry.domain.repositories.LegalRepository
import singleregistry.domain.repositories.PersonRepository
import singleregistry.resources.security.UserDetailsImpl

@Service
class PersonService(
    private val personRepository: PersonRepository,
    private val legalPersonRepository: LegalRepository,
    private val individualRepository: IndividualRepository
) {

    fun findByPersonId(): Any? {
        val person = personRepository.findByEmail(authenticated()?.username)

        return when(person?.type) {
             PersonType.PF -> IndividualPersonResponse(individual = individualRepository.findByPersonPersonId(person.personId))
             PersonType.PJ -> LegalPersonResponse(legal = legalPersonRepository.findByPersonPersonId(person.personId))
            else -> null
        }
    }

    private fun authenticated(): UserDetailsImpl? {
        return try {
            SecurityContextHolder.getContext().authentication.principal as UserDetailsImpl
        } catch (e: Exception) {
            null
        }
    }
}