package singleregistry.domain.services

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import singleregistry.domain.entities.individual.Individual
import singleregistry.domain.exceptions.DataValidationException
import singleregistry.domain.exceptions.IndividualPersonNotFoundException
import singleregistry.domain.exceptions.LegalPersonNotFoundException
import singleregistry.domain.repositories.IndividualRepository
import singleregistry.domain.repositories.PersonRepository

@Service
class IndividualService(
    private val personRepository: PersonRepository,
    private val individualRepository: IndividualRepository
) {

    private companion object {
        val logger: Logger = LoggerFactory.getLogger(IndividualService::class.java.name)
    }

    fun create(individual: Individual): Individual {
        validateDate(individual)

        logger.info("Individual legal person")

        personRepository.save(individual.person).also { person ->
            val newIndividual = individualRepository.save(individual.copy(person = person))
            logger.info("Individual[${newIndividual._id}] with mongo database created")

            return newIndividual
        }

    }

    fun findByCpf(cpf: String) =
        individualRepository.findByCpf(cpf) ?: throw IndividualPersonNotFoundException("Person not found by cpf: $cpf")

    private fun validateDate(individual: Individual) {
        val error = mutableListOf<String>()

        individual.let {
            if (personRepository.existsByEmail(it.person.email)) {
                error.add("Email: ${individual.person.email} already use")
            }
        }
        individual.cpf.let {
            if (individualRepository.existsByCpf(it)) {
                error.add("CPF: ${individual.cpf} already use")
            }
        }
        individual.person.phone.let {
            if (personRepository.existsByPhone(it)) {
                error.add(
                    "Phone: ${individual.person.phone?.countryCode}" +
                            "${individual.person.phone?.areaCode}" +
                            "${individual.person.phone?.number} already use"
                )
            }
        }

        if (error.isNotEmpty()) {
            throw DataValidationException(error)
        }
    }
}