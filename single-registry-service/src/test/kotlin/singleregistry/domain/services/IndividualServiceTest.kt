package singleregistry.domain.services

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import singleregistry.domain.exceptions.DataValidationException
import singleregistry.domain.repositories.IndividualRepository
import singleregistry.domain.repositories.PersonRepository
import singleregistry.factories.IndividualFactory
import singleregistry.resources.authorization.gateways.AuthorizationServiceImpl

class IndividualServiceTest {
    private lateinit var individualRepository: IndividualRepository
    private lateinit var personRepository: PersonRepository
    private lateinit var individualService: IndividualService
    private lateinit var authorizationServiceImpl: AuthorizationServiceImpl


    @BeforeEach
    fun beforeEach() {
        authorizationServiceImpl = mockk(relaxed = true)
        personRepository = mockk(relaxed = true)
        individualRepository = mockk(relaxed = true)
        individualService = IndividualService(personRepository, individualRepository, authorizationServiceImpl)
    }

    @Test
    fun `given a valid individual person, should be create a legal and person in database`() {
        val individual = IndividualFactory.sample()

        every { personRepository.save(individual.person) } returns individual.person
        every { individualRepository.save(individual) } returns individual

        individualService.create(individual, "test")

        verify { personRepository.save(any()) }
        verify { individualRepository.save(any()) }
    }

    @Test
    fun `given a existing person email , should be return DataValidationException`() {
        val individual = IndividualFactory.sample()
        val error = listOf("Email: ${individual.person.email} already use")

        every { personRepository.existsByEmail(individual.person.email) } returns true

        val response = assertThrows<DataValidationException> {
            individualService.create(individual, "test")
        }

        Assertions.assertEquals(error, response.details())
    }

    @Test
    fun `given a existing cpf , should be return DataValidationException`() {
        val individual = IndividualFactory.sample()
        val error = listOf("CPF: ${individual.cpf} already use")

        every { individualRepository.existsByCpf(individual.cpf) } returns true

        val response = assertThrows<DataValidationException> {
            individualService.create(individual, "test")
        }

        Assertions.assertEquals(error, response.details())
    }

    @Test
    fun `given a existing phone, should be return DataValidationException`() {
        val individual = IndividualFactory.sample()
        val error = listOf(
            "Phone: ${individual.person.phone?.countryCode}" +
                    "${individual.person.phone?.areaCode}" +
                    "${individual.person.phone?.number} already use"
        )

        every {
            personRepository.existsByPhone(individual.person.phone)
        } returns true

        val response = assertThrows<DataValidationException> {
            individualService.create(individual, "test")
        }

        Assertions.assertEquals(error, response.details())
    }

    @Test
    fun `given a valid cpf, should return a individual person`() {
        val individual = IndividualFactory.sample()
        val cpf = "221.670.888-76"

        every { individualRepository.findByCpf(any()) } returns individual

        individualService.findByCpf(cpf)

        verify { individualRepository.findByCpf(cpf) }
    }
}