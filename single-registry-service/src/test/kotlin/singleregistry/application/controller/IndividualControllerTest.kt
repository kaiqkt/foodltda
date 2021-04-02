package singleregistry.application.controller

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import singleregistry.application.controllers.IndividualController
import singleregistry.application.dto.toDomain
import singleregistry.domain.exceptions.ResultBindingException
import singleregistry.domain.services.IndividualService
import singleregistry.factories.IndividualFactory
import singleregistry.factories.IndividualRequestFactory

class IndividualControllerTest {
    private lateinit var individualService: IndividualService
    private lateinit var individualController: IndividualController
    private lateinit var result: BindingResult

    @BeforeEach
    fun beforeEach() {
        individualService =  mockk(relaxed = true)
        individualController = IndividualController(individualService)
        result = mockk(relaxed = true)
    }

    @Test
    fun `given valid individual request should return legal and http status 201`() {
        val request = IndividualRequestFactory.sample()
        val individual = IndividualFactory.sample()

        every { individualService.create(request.toDomain(), request.password) } returns individual

        val controller = individualController.register(request, result)

        verify { individualService.create(any(), any()) }
        Assertions.assertEquals(HttpStatus.CREATED, controller.statusCode)
    }

    @Test
    fun `given invalid individual request should return a resultBindingException`() {
        val request = IndividualRequestFactory.sample()

        every { result.hasErrors() } returns true

        assertThrows<ResultBindingException> {
            individualController.register(request, result)
        }
    }

    @Test
    fun `given valid cpf request should return a legal person`() {
        val individual = IndividualFactory.sample()
        val cpf = "221.670.888-76"

        every { individualService.findByCpf(any()) } returns individual

        val controller = individualController.getByCpf(cpf)

        verify { individualService.findByCpf(cpf) }
        Assertions.assertEquals(HttpStatus.ACCEPTED, controller.statusCode)
        Assertions.assertEquals(individual, controller.body)
    }

}