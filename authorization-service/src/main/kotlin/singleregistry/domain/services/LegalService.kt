package singleregistry.domain.services

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import singleregistry.domain.entities.Legal
import singleregistry.domain.repositories.LegalRepository

@Service
class LegalService(
    private val legalRepository: LegalRepository
) {
    companion object

    val logger: Logger = LoggerFactory.getLogger(LegalService::class.java.name)

    fun create(legal: Legal) {
        validateDate(legal)

        legalRepository.save(legal)

    }

    private fun validateDate(legal: Legal) {
        legal.let {
            if (legalRepository.existsByEmail(it.email)) {

                logger.error("Email: ${legal.email} already use")

//                throw DataAlreadyInUseException()
            }
        }
        legal.cnpj?.let {
            if (legalRepository.existsByCnpj(it)) {

                logger.error("CNPJ: ${legal.cnpj} already use")

//                throw DataAlreadyInUseException()
            }
        }
        legal.phone.let {
            if (legalRepository.existsByPhoneCountryCodeAndPhoneAreaCodeAndPhoneNumber(
                    legal.phone.countryCode,
                    legal.phone.areaCode,
                    legal.phone.number
                )
            ) {

                logger.error("Phone: ${legal.phone.countryCode}${legal.phone.areaCode}${legal.phone.number} already use")

//                throw DataAlreadyInUseException()()
            }
        }
    }
}