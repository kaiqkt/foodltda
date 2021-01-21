package com.foodltda.merchantservice.domain.service

import com.foodltda.merchantservice.application.dto.Owner
import com.foodltda.merchantservice.application.dto.request.RestaurantRegistrationDTO
import com.foodltda.merchantservice.application.dto.response.Response
import com.foodltda.merchantservice.domain.entities.Restaurant
import com.foodltda.merchantservice.domain.validation.ResultValidation
import com.foodltda.merchantservice.resouce.repositories.RestaurantRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError

@Service
class RestaurantService(val restaurantRepository: RestaurantRepository, val legalPersonService: LegalPersonService) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(RestaurantService::class.java.name)
    }

    fun register(personId: String, restaurant: RestaurantRegistrationDTO, response: Response<Any>, result: BindingResult) {
        checkDataAvailability(restaurant.name, restaurant.telephone, result)
        ResultValidation.check(response, result)

        val owner: Owner = legalPersonService.currentPerson(personId).get().let {
            Owner(
                    name = it.name,
                    cnpj = it.cnpj,
                    telephone = it.cnpj
            )
        }

        restaurantRepository.save(Restaurant.fromDocument(owner, restaurant))
    }

    private fun checkDataAvailability(name: String?, telephone: String?, result: BindingResult): BindingResult {
        name?.let {
            if (restaurantRepository.existsByRestaurantName(it)) {
                result.addError(ObjectError("restaurant", "Restaurant name $name already use.")).let { log ->
                    logger.error(log.toString())
                }
                return result
            }
        }
        telephone?.let {
            if (restaurantRepository.existsByTelephone(it)) {
                result.addError(ObjectError("restaurant", "Telephone: $telephone already use.")).let { log ->
                    logger.error(log.toString())
                }
                return result
            }
        }

        return result
    }
}