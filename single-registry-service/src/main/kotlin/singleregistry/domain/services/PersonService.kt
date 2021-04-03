package singleregistry.domain.services

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import singleregistry.domain.entities.person.PersonType
import singleregistry.domain.repositories.IndividualRepository
import singleregistry.domain.repositories.LegalRepository
import singleregistry.domain.repositories.PersonRepository
import singleregistry.resources.security.UserDetailsImpl

@Service
class PersonService(
    private val personRepository: PersonRepository,
    private val legalPersonRepository: LegalRepository,
    private val individualRepository: IndividualRepository,
) {

    fun findByToken(): Any? {
        val person = personRepository.findByEmail(authenticated()?.username)

        return when(person?.type) {
             PersonType.PF -> individualRepository.findByPersonPersonId(person.personId)
             PersonType.PJ -> legalPersonRepository.findByPersonPersonId(person.personId)
            else -> null
        }
    }

    fun findByPersonId(personId: String?): Any? {
        val person = personRepository.findByPersonId(personId)

        return when(person?.type) {
            PersonType.PF -> individualRepository.findByPersonPersonId(person.personId)
            PersonType.PJ -> legalPersonRepository.findByPersonPersonId(person.personId)
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