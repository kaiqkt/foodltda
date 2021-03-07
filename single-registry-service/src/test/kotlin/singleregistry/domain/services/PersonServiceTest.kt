package singleregistry.domain.services

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import singleregistry.application.dto.PersonResponse
import singleregistry.domain.entities.person.PersonType
import singleregistry.domain.exceptions.PersonNotFoundException
import singleregistry.domain.repositories.IndividualRepository
import singleregistry.domain.repositories.LegalRepository
import singleregistry.domain.repositories.PersonRepository
import singleregistry.factories.LegalFactory
import singleregistry.factories.PersonFactory

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
        val personId = "0001"

        every { personRepository.findByPersonId(any()) } returns person

        val response = personService.findByPersonId(personId)

        verify { personRepository.findByPersonId(personId) }
        Assertions.assertNotNull(response)
    }

    @Test
    fun `given a valid person id, should return a individual person`() {
        val person = PersonFactory.sample(personType = PersonType.PF)
        val personId = "0001"

        every { personRepository.findByPersonId(any()) } returns person

        val response = personService.findByPersonId(personId)

        verify { personRepository.findByPersonId(personId) }
        Assertions.assertNotNull(response)
    }

    @Test
    fun `given a invalid person id, should return a exception`() {
        val personId = "0001"
        val error = "Person not found by id: $personId"

        every { personRepository.findByPersonId(any()) } returns null

        val response = assertThrows<PersonNotFoundException> {
            personService.findByPersonId(personId)
        }

        Assertions.assertEquals(error, response.message)
    }

    @Test
    fun `given a invalid person type, should return a exception`() {
        val person = PersonFactory.sample(personType = null)
        val error = "Invalid person type: ${person.type}"
        val personId = "0001"

        every { personRepository.findByPersonId(any()) } returns person

        val response = assertThrows<IllegalArgumentException> {
            personService.findByPersonId(personId)
        }

        Assertions.assertEquals(error, response.message)
    }
}