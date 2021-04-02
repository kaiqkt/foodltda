package singleregistry.application.controller

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import singleregistry.application.controllers.PersonController
import singleregistry.application.dto.IndividualPersonResponse
import singleregistry.application.dto.LegalPersonResponse
import singleregistry.domain.services.PersonService
import singleregistry.factories.LegalFactory

class PersonControllerTest {
    private lateinit var personService: PersonService
    private lateinit var personController: PersonController

    @BeforeEach
    fun beforeEach() {
        personService =  mockk(relaxed = true)
        personController = PersonController(personService)
    }

    @Test
    fun `given valid person id should return person and http status 200`() {
        val response = LegalPersonResponse(legal = LegalFactory.sample())

        every { personService.findByPersonId() } returns response

        val controller = personController.getByPersonId()

        verify { personService.findByPersonId() }
        Assertions.assertEquals(HttpStatus.ACCEPTED, controller.statusCode)
    }
}