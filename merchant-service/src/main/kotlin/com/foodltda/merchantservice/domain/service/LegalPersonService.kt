package com.foodltda.merchantservice.domain.service

import com.foodltda.merchantservice.application.dto.Address
import com.foodltda.merchantservice.application.dto.request.PersonRegistrationDTO
import com.foodltda.merchantservice.application.dto.request.UpdatePerson
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
import javax.validation.constraints.Email

@Service
class LegalPersonService(
        val legalPersonRepository: LegalPersonRepository,
        val loginPersonRepository: LoginPersonRepository
) {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(LegalPersonService::class.java.name)
    }

    fun register(person: PersonRegistrationDTO, response: Response<Any>, result: BindingResult) {
        checkDataAvailability(person.email, person.cnpj, person.telephone, result)
        ResultValidation.check(response, result)


//        user.password = bCryptPasswordEncoder.encode(user.password)

        legalPersonRepository.save(LegalPerson.fromDocument(person)).let {
            loginPersonRepository.save(LoginUser.fromDocument(it))
            logger.info("Creating legal person for legalPersonId: ${it.id}")
        }
    }

    fun update(personId: String, person: UpdatePerson, response: Response<Any>, result: BindingResult): Response<Any> {
        checkDataAvailability(person.email, null, person.telephone, result)
        ResultValidation.check(response, result)

        val legalPerson = currentPerson(personId)
//        val loginUser = currentUser(personId)


        val updatePerson = legalPerson.get().copy(
                id = legalPerson.get().id,
                name = person.name ?: legalPerson.get().name,
                email = person.email ?: legalPerson.get().email,
                cnpj = legalPerson.get().cnpj,
                address = person.address ?: legalPerson.get().address,
                password = person.password ?: legalPerson.get().password,
                telephone = person.telephone ?: legalPerson.get().telephone
        )


//        val updateLogin = loginUser.get().copy(
//                id = loginUser.get().id,
//                email = person.email ?: loginUser.get().email,
//                password = person.password ?: loginUser.get().password
//        )

        legalPersonRepository.save(updatePerson).let {
            loginPersonRepository.save(LoginUser.fromDocument(it))
            logger.info("Update legal person for legalPersonId: ${it.id}")
        }

        response.data = updatePerson

        return response
    }

    fun currentPerson(personId: String) = legalPersonRepository.findById(personId)
    fun currentUser(userId: String) = loginPersonRepository.findById(userId)

//    fun currentUser() = legalPersonRepository.findByEmail(authenticated().username)

//    private fun authenticated(): UserDetailsImpl? {
//        return try {
//            SecurityContextHolder.getContext().authentication.principal as UserDetailsImpl
//        } catch (e: Exception) {
//            null
//        }
//    }

//    private fun newPassword(password: String?): String? {
//        return if (password != null) {
//            BCrypt.hashpw(password, BCrypt.gensalt())
//        } else {
//            null
//        }
//    }


    private fun checkDataAvailability(email: String?, cnpj: String?, telephone: String?, result: BindingResult): BindingResult {
        email?.let {
            if (legalPersonRepository.existsByEmail(it)) {
                result.addError(ObjectError("legalPerson", "Email already use."))
                logger.error("Email: $email already use")
                return result
            }
        }
        cnpj?.let {
            if (legalPersonRepository.existsByCnpj(it)) {
                result.addError(ObjectError("legalPerson", "CNPJ already use."))
                logger.error("CNPJ: $cnpj already use")
                return result
            }
        }
        telephone?.let {
            if (legalPersonRepository.existsByTelephone(it)) {
                result.addError(ObjectError("legalPerson", "Telephone already use."))
                logger.error("Telephone: $telephone already use")
                return result
            }
        }

        return result
    }
}
