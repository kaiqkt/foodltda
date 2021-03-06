package singleregistry.domain.services

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import singleregistry.domain.exceptions.DataValidationException
import singleregistry.domain.exceptions.IndividualPersonNotFoundException
import singleregistry.domain.exceptions.LegalPersonNotFoundException
import singleregistry.domain.repositories.IndividualRepository
import singleregistry.domain.repositories.LegalRepository
import singleregistry.domain.repositories.PersonRepository
import singleregistry.factories.IndividualFactory
import singleregistry.factories.LegalFactory

class IndividualServiceTest {
    private lateinit var individualRepository: IndividualRepository
    private lateinit var personRepository: PersonRepository
    private lateinit var individualService: IndividualService


    @BeforeEach
    fun beforeEach() {
        personRepository = mockk(relaxed = true)
        individualRepository = mockk(relaxed = true)
        individualService = IndividualService(personRepository, individualRepository)
    }

    @Test
    fun `given a valid individual person, should be create a legal and person in database`() {
        val individual = IndividualFactory.sample()

        every { personRepository.save(individual.person) } returns individual.person
        every { individualRepository.save(individual) } returns individual

        individualService.create(individual)

        verify { personRepository.save(any()) }
        verify { individualRepository.save(any()) }
    }

    @Test
    fun `given a existing person email , should be return DataValidationException`() {
        val individual = IndividualFactory.sample()
        val error = listOf("Email: ${individual.person.email} already use")

        every { personRepository.existsByEmail(individual.person.email) } returns true

        val response = assertThrows<DataValidationException> {
            individualService.create(individual)
        }

        Assertions.assertEquals(error, response.details())
    }

    @Test
    fun `given a existing cpf , should be return DataValidationException`() {
        val individual = IndividualFactory.sample()
        val error = listOf("CPF: ${individual.cpf} already use")

        every { individualRepository.existsByCpf(individual.cpf) } returns true

        val response = assertThrows<DataValidationException> {
            individualService.create(individual)
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
            individualService.create(individual)
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

    @Test
    fun `given a invalid cnpj, should be return LegalPersonNotFoundException`() {
        val cpf = "221.670.888-76"
        val error = "Person not found by cpf: $cpf"

        every { individualRepository.findByCpf(any()) } returns null

        val response = assertThrows<IndividualPersonNotFoundException> {
            individualService.findByCpf(cpf)
        }

        Assertions.assertEquals(error, response.message)
    }
}