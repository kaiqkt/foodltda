package singleregistry.domain.services

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import singleregistry.domain.entities.person.PersonType
import singleregistry.domain.repositories.IndividualRepository
import singleregistry.domain.repositories.LegalRepository
import singleregistry.domain.repositories.PersonRepository
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

        every { personRepository.findByEmail(person.email) } returns person

        val response = personService.findByPersonId()

        verify { personRepository.findByEmail(any()) }
        Assertions.assertNotNull(response)
    }

    @Test
    fun `given a valid person id, should return a individual person`() {
        val person = PersonFactory.sample(personType = PersonType.PF)

        every { personRepository.findByEmail(person.email) } returns person

        val response = personService.findByPersonId()

        verify { personRepository.findByEmail(any()) }
        Assertions.assertNotNull(response)
    }


    @Test
    fun `given a invalid person type, should return a exception`() {
        val email = "teste@test.com"

        every { personRepository.findByEmail(email) } returns null

        val response = personService.findByPersonId()

        Assertions.assertNull(response)
    }
}