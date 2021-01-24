package com.foodltda.merchantservice.domain.service

import com.foodltda.merchantservice.domain.entities.Owner
import com.foodltda.merchantservice.application.dto.request.RestaurantRegistrationDTO
import com.foodltda.merchantservice.application.dto.request.UpdateRestaurant
import com.foodltda.merchantservice.application.dto.response.Response
import com.foodltda.merchantservice.domain.entities.Restaurant
import com.foodltda.merchantservice.domain.entities.enums.Payment
import com.foodltda.merchantservice.domain.exceptions.OwnerException
import com.foodltda.merchantservice.domain.exceptions.TagNotFoundException
import com.foodltda.merchantservice.domain.validation.ResultValidation
import com.foodltda.merchantservice.resouce.repositories.FoodCategoryRespository
import com.foodltda.merchantservice.resouce.repositories.RestaurantRepository
import com.github.slugify.Slugify
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import java.util.*

@Service
class RestaurantService(val restaurantRepository: RestaurantRepository, val legalPersonService: LegalPersonService, val tagRepository: FoodCategoryRespository) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(RestaurantService::class.java.name)
    }

    fun register(personId: String, restaurant: RestaurantRegistrationDTO, response: Response<Any>, result: BindingResult): Response<Any> {
        val owner: Owner = legalPersonService.currentPerson(personId).get().let {
            Owner(
                    id = it.id,
                    name = it.name,
                    cnpj = it.cnpj,
                    telephone = it.cnpj
            )
        }

        checkDataAvailability(restaurant.name, restaurant.telephone, owner.cnpj, result)
        ResultValidation.check(response, result)


        val tagList = restaurant.foodCategory?.map { tag ->
            tagRepository.findByName(tag) ?: throw TagNotFoundException("Tag: $tag not be exist")
        }?.toMutableList()

        response.data = restaurantRepository.save(Restaurant.fromDocument(owner, restaurant, tagList))

        logger.info("Save new restaurant: ${(response.data as Restaurant).id}.")

        return response

    }

    fun update(slug: String, personId: String, restaurant: UpdateRestaurant, response: Response<Any>, result: BindingResult): Response<Any> {
        checkDataAvailability(restaurant.name, restaurant.telephone, null, result)
        ResultValidation.check(response, result)

        getRestaurant(slug)?.let {
            val person = legalPersonService.currentPerson(personId)
            if (it.owner.cnpj != person.get().cnpj) {
                throw OwnerException("An id: $personId person is not related to the restaurant")
            }


            var slug: String? = it.slug


            restaurant.name?.let { new ->
                if (new != it.name) {
                    slug = Slugify().slugify(restaurant.name)
                    if (restaurantRepository.existsBySlug(slug!!)) {
                        slug += "-" + UUID.randomUUID().toString().substring(0, 8)
                    }
                }
            }


            val tagList = restaurant.foodCategory?.map { tag ->
                tagRepository.findByName(tag) ?: throw TagNotFoundException("Tag: $tag not be exist")
            }?.toMutableList()


            val update = it.copy(
                    id = it.id,
                    slug = slug,
                    name = restaurant.name ?: it.name,
                    owner = it.owner,
                    image = restaurant.image ?: it.image,
                    address = restaurant.address ?: it.address,
                    telephone = restaurant.telephone ?: it.telephone,
                    deliveryTime = restaurant.deliveryTime ?: it.deliveryTime,
                    foodCategory = if (tagList.isNullOrEmpty()) it.foodCategory else tagList,
                    paymentMethods = restaurant.paymentMethods ?: it.paymentMethods
            )

            restaurantRepository.save(update)
            response.data = mapOf("restaurant" to update)
            logger.info("Update restaurant: ${it.id}.")
        }

        return response
    }

    fun getBy(tag: String?, name: String?, payment: Payment?, page: PageRequest, response: Response<Any>): Response<Any> {
        response.data = when {
            !tag.isNullOrBlank() -> restaurantRepository.findByFoodCategoryName(tag, page)
            !name.isNullOrBlank() -> restaurantRepository.findByName(name, page)
            payment != null -> restaurantRepository.findByPaymentMethods(payment, page)
            else -> restaurantRepository.findAll(page).toList()
        }
        return response
    }

    fun getRestaurant(slug: String) = restaurantRepository.findBySlug(slug)

    fun getRestaurantByPersonId(personId: String, response: Response<Any>): Response<Any> {
        val restaurant = restaurantRepository.findByOwnerId(personId)

        if (restaurant != null) {
            response.data = restaurantRepository.findByOwnerId(personId)

            return response
        }

        throw OwnerException("Restaurant not found by person id: $personId")
    }

    private fun checkDataAvailability(name: String?, telephone: String?, cnpj: String?, result: BindingResult): BindingResult {
        name?.let {
            if (restaurantRepository.existsByName(it)) {
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

        cnpj?.let {
            if (restaurantRepository.existsByOwnerCnpj(it)) {
                result.addError(ObjectError("restaurant", "CNPJ: $cnpj already use.")).let { log ->
                    logger.error(log.toString())
                }
                return result
            }
        }

        return result
    }
}