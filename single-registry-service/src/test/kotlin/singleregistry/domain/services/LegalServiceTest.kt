package singleregistry.domain.services

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest
import singleregistry.domain.exceptions.DataValidationException
import singleregistry.domain.repositories.LegalRepository
import singleregistry.domain.repositories.PersonRepository
import singleregistry.factories.LegalFactory

class LegalServiceTest {
    private lateinit var legalRepository: LegalRepository
    private lateinit var personRepository: PersonRepository
    private lateinit var legalService: LegalService

    @BeforeEach
    fun beforeEach() {
        legalRepository = mockk(relaxed = true)
        personRepository = mockk(relaxed = true)
        legalService = LegalService(legalRepository, personRepository)
    }

    @Test
    fun `given a valid legal person, should be create a legal and person in database`() {
        val legal = LegalFactory.sample()

        every { personRepository.save(legal.person) } returns legal.person
        every { legalRepository.save(legal) } returns legal

        legalService.create(legal)

        verify { personRepository.save(any()) }
        verify { legalRepository.save(any()) }
    }

    @Test
    fun `given a existing person email , should be return DataValidationException`() {
        val legal = LegalFactory.sample()
        val error = listOf("Email: ${legal.person.email} already use")

        every { legalRepository.existsByPersonEmail(legal.person.email) } returns true

        val response = assertThrows<DataValidationException> {
            legalService.create(legal)
        }

        Assertions.assertEquals(error, response.details())
    }

    @Test
    fun `given a existing cnpj , should be return DataValidationException`() {
        val legal = LegalFactory.sample()
        val error = listOf("CNPJ: ${legal.cnpj} already use")

        every { legalRepository.existsByCnpj(legal.cnpj) } returns true

        val response = assertThrows<DataValidationException> {
            legalService.create(legal)
        }

        Assertions.assertEquals(error, response.details())
    }

    @Test
    fun `given a existing phone, should be return DataValidationException`() {
        val legal = LegalFactory.sample()
        val error = listOf(
            "Phone: ${legal.person.phone?.countryCode}" +
                    "${legal.person.phone?.areaCode}" +
                    "${legal.person.phone?.number} already use"
        )

        every {
            legalRepository.existsByPersonPhone(legal.person.phone)
        } returns true

        val response = assertThrows<DataValidationException> {
            legalService.create(legal)
        }

        Assertions.assertEquals(error, response.details())
    }
}