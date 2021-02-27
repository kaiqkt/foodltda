package singleregistry.domain.services

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import singleregistry.domain.entities.person.PersonType
import singleregistry.domain.exceptions.PersonNotFoundException
import singleregistry.domain.repositories.PersonRepository
import singleregistry.factories.PersonFactory

class PersonServiceTest {
    private lateinit var personRepository: PersonRepository
    private lateinit var personService: PersonService

    @BeforeEach
    fun beforeEach() {
        personRepository = mockk(relaxed = true)
        personService = PersonService(personRepository)
    }

    @Test
    fun `given a valid person id, should return a person`() {
        val person = PersonFactory.sample(personType = PersonType.PJ)
        val personId = "0001"

        every { personRepository.findByPersonId(any()) } returns person

        personService.findByPersonId(personId)

        verify { personRepository.findByPersonId(personId) }
    }

    @Test
    fun `given a invalid person id, should return a person`() {
        val personId = "0001"
        val error = "Person not found by id: $personId"

        every { personRepository.findByPersonId(any()) } returns null

        val response = assertThrows<PersonNotFoundException> {
            personService.findByPersonId(personId)
        }

        Assertions.assertEquals(error, response.message)
    }
}