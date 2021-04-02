package singleregistry.domain.services

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import singleregistry.domain.entities.person.PersonType
import singleregistry.domain.repositories.IndividualRepository
import singleregistry.domain.repositories.LegalRepository
import singleregistry.domain.repositories.PersonRepository
import singleregistry.factories.IndividualFactory
import singleregistry.factories.PersonFactory
import singleregistry.resources.security.UserDetailsImpl

class PersonServiceTest {
    private lateinit var personRepository: PersonRepository
    private lateinit var individualRepository: IndividualRepository
    private lateinit var legalRepository: LegalRepository
    private lateinit var personService: PersonService

    @BeforeEach
    fun beforeEach() {
        personRepository = mockk(relaxed = true)
        individualRepository = mockk(relaxed = true)
        legalRepository = mockk(relaxed = true)
        personService = PersonService(personRepository, legalRepository, individualRepository)
    }

    @Test
    fun `given a valid person id, should return a legal person`() {
        val person = PersonFactory.sample(personType = PersonType.PJ)

        every { personRepository.findByEmail(person.email) } returns person

        val response = personService.findByPersonId()

        verify { personRepository.findByEmail(any()) }
        verify { legalRepository.findByPersonPersonId(any()) }
        Assertions.assertNotNull(response)
    }

    @Test
    fun `given a valid person id, should return a individual person`() {
        val person = PersonFactory.sample(personType = PersonType.PF)
        val individual = IndividualFactory.sample()

        val userDetails = UserDetailsImpl(person)

        SecurityContextHolder.getContext().authentication =
            UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)

        every { personRepository.findByEmail(person.email) } returns person
        every { individualRepository.findByPersonPersonId(person.personId) } returns individual

        val response = personService.findByPersonId()


        verify { personRepository.findByEmail(any()) }
        verify { individualRepository.findByPersonPersonId(any()) }
        Assertions.assertNotNull(response)
    }


    @Test
    fun `given a invalid person type, should return null`() {

        every { personRepository.findByEmail(any()) } returns null

        val response = personService.findByPersonId()

        Assertions.assertNull(response)
    }
}