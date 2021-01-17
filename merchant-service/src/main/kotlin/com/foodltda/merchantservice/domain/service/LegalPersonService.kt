package com.foodltda.merchantservice.domain.service

import com.foodltda.merchantservice.application.dto.request.PersonRegistrationDTO
import com.foodltda.merchantservice.application.dto.response.Response
import com.foodltda.merchantservice.domain.entities.LegalPerson
import com.foodltda.merchantservice.domain.entities.LoginUser
import com.foodltda.merchantservice.domain.validation.ResultValidation
import com.foodltda.merchantservice.resouce.repositories.LegalPersonRepository
import com.foodltda.merchantservice.resouce.repositories.LoginPersonRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import org.springframework.stereotype.Service
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError

@Service
class LegalPersonService(
        val legalPersonRepository: LegalPersonRepository,
        val loginPersonRepository: LoginPersonRepository
) {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(LegalPersonService::class.java.name)
    }

    fun register(person: PersonRegistrationDTO, response: Response<Any>, result: BindingResult) {
        checkDataAvailability(person, result)
        ResultValidation.check(response, result)

        legalPersonRepository.save(LegalPerson.fromDocument(person)).let {
            loginPersonRepository.save(LoginUser.fromDocument(it))
            logger.info("Creating legal person for legalPersonId: ${it.id}")
        }
    }

    private fun checkDataAvailability(person: PersonRegistrationDTO, result: BindingResult): BindingResult {
        person.email.let { email ->
            if (legalPersonRepository.existsByEmail(email)) {
                result.addError(ObjectError("legalPerson", "Email already use."))
                logger.error("Email: $email already use")
                return result
            }
        }
        person.cnpj.let { cnpj ->
            if (legalPersonRepository.existsByCnpj(cnpj)) {
                result.addError(ObjectError("legalPerson", "CNPJ already use."))
                logger.error("CNPJ: $cnpj already use")
                return result
            }
        }
        return result
    }
}
