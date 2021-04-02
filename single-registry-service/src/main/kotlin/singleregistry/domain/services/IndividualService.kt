package singleregistry.domain.services

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import singleregistry.domain.entities.individual.Individual
import singleregistry.resources.authorization.entities.User
import singleregistry.domain.exceptions.DataValidationException
import singleregistry.domain.repositories.IndividualRepository
import singleregistry.domain.repositories.PersonRepository
import singleregistry.resources.authorization.gateways.AuthorizationServiceImpl

@Service
class IndividualService(
    private val personRepository: PersonRepository,
    private val individualRepository: IndividualRepository,
    private val authorizationServiceImpl: AuthorizationServiceImpl
) {

    private companion object {
        val logger: Logger = LoggerFactory.getLogger(IndividualService::class.java.name)
    }

    fun create(individual: Individual, password: String): Individual {
        validateDate(individual)


        personRepository.save(individual.person).also { person ->
            val newIndividual = individualRepository.save(individual.copy(person = person))

            val user = User(
                personId = person.personId,
                email = person.email,
                password = password,
                countryCode = person.phone?.countryCode,
                areaCode = person.phone?.areaCode,
                phoneNumber = person.phone?.number
            )

            authorizationServiceImpl.register(user)

            logger.info("Individual[${newIndividual._id}] created ")
            return newIndividual
        }

    }

    fun findByCpf(cpf: String) = individualRepository.findByCpf(cpf)

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