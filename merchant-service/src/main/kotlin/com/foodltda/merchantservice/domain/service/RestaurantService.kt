package com.foodltda.merchantservice.domain.service

import com.foodltda.merchantservice.application.dto.request.RestaurantRegistrationDTO
import com.foodltda.merchantservice.application.dto.request.UpdateRestaurant
import com.foodltda.merchantservice.application.dto.response.OpeningHours
import com.foodltda.merchantservice.application.dto.response.Response
import com.foodltda.merchantservice.application.dto.response.RestaurantDTO
import com.foodltda.merchantservice.application.dto.response.RestaurantsDTO
import com.foodltda.merchantservice.domain.entities.DeliveryTime
import com.foodltda.merchantservice.domain.entities.Restaurant
import com.foodltda.merchantservice.domain.entities.enums.Payment
import com.foodltda.merchantservice.domain.exceptions.OwnerException
import com.foodltda.merchantservice.domain.exceptions.RestaurantNotFoundException
import com.foodltda.merchantservice.domain.exceptions.TagNotFoundException
import com.foodltda.merchantservice.domain.validation.ResultValidation
import com.foodltda.merchantservice.resouce.repositories.RestaurantCategoryRepository
import com.foodltda.merchantservice.resouce.repositories.RestaurantRepository
import com.github.slugify.Slugify
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@Service
class RestaurantService(val restaurantRepository: RestaurantRepository, val legalPersonService: LegalPersonService, val restaurantCategoryRepository: RestaurantCategoryRepository) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(RestaurantService::class.java.name)
    }

    fun register(personId: String, restaurant: RestaurantRegistrationDTO, response: Response<Any>, result: BindingResult): Response<Any> {
        checkDataAvailability(restaurant.name, restaurant.telephone, personId, result)
        ResultValidation.check(response, result)


        val tag = restaurant.restaurantCategory?.let { tag ->
            restaurantCategoryRepository.findByName(tag) ?: throw TagNotFoundException("Tag: $tag not be exist")
        }

        response.data = restaurantRepository.save(Restaurant.fromDocument(personId, restaurant, tag))

        logger.info("Save new restaurant: ${(response.data as Restaurant).id}.")

        return response

    }

    fun update(slug: String, personId: String, restaurant: UpdateRestaurant, response: Response<Any>, result: BindingResult): Response<Any> {
        checkDataAvailability(restaurant.name, restaurant.telephone, null, result)
        ResultValidation.check(response, result)

        getRestaurant(slug)?.let {
            val person = legalPersonService.currentPerson(personId)
            if (it.legalPersonId != person.get().id) {
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


            val tag = restaurant.foodCategory?.let { tag ->
                restaurantCategoryRepository.findByName(tag) ?: throw TagNotFoundException("Tag: $tag not be exist")
            }


            val update = it.copy(
                    id = it.id,
                    slug = slug,
                    name = restaurant.name ?: it.name,
                    legalPersonId = it.legalPersonId,
                    image = restaurant.image ?: it.image,
                    address = restaurant.address ?: it.address,
                    telephone = restaurant.telephone ?: it.telephone,
                    deliveryTime = restaurant.deliveryTime ?: it.deliveryTime,
                    restaurantCategory = tag ?: it.restaurantCategory,
                    paymentMethods = restaurant.paymentMethods ?: it.paymentMethods
            )

            restaurantRepository.save(update)
            response.data = mapOf("restaurant" to update)
            logger.info("Update restaurant: ${it.id}.")

            return response
        }

        throw RestaurantNotFoundException("Restaurant: $slug not be exist")
    }

    fun getBy(tag: String?, name: String?, payment: Payment?, page: PageRequest, response: Response<Any>): Response<Any> {
        val restaurants = when {
            !tag.isNullOrBlank() -> restaurantRepository.findByFoodCategoryName(tag, page)
            !name.isNullOrBlank() -> restaurantRepository.findByName(name, page)
            payment != null -> restaurantRepository.findByPaymentMethods(payment, page)
            else -> restaurantRepository.findAll(page).toList()
        }

        val open: MutableList<RestaurantsDTO> = arrayListOf()
        val close: MutableList<RestaurantsDTO> = arrayListOf()

        restaurants.map {res ->
            val h = openingHours(res.deliveryTime)
            if (h.first) {
                open.add(RestaurantsDTO(id = res.id, name = res.name, slug = res.slug, image = res.image, time = h.second, openingHours = OpeningHours.OPEN))
            } else {
                close.add(RestaurantsDTO(id = res.id, name = res.name,  slug = res.slug, image = res.image, time = h.second, openingHours = OpeningHours.CLOSED))
            }
        }
        open.addAll(close)

        response.data = open

        return response
    }

    fun openingHours(time: MutableList<DeliveryTime>): Pair<Boolean, DeliveryTime?> {
        time.map {
            if (LocalDate.now().dayOfWeek == it.dayOfWeek) {
                if (LocalTime.now().isBefore(LocalTime.parse(it.closeThe)) && LocalTime.now().isAfter(LocalTime.parse(it.openThe))) {
                    return Pair(true, it)
                }
                return Pair(false, it)
            }
        }

        return Pair(false, null)
    }

    fun getRestaurant(slug: String): Restaurant? = restaurantRepository.findBySlug(slug)

    fun getRestaurantByPersonId(personId: String, response: Response<Any>): Response<Any> {
        val restaurant = restaurantRepository.findByLegalPersonId(personId)

        if (restaurant != null) {
            response.data = restaurant

            return response
        }

        throw OwnerException("Restaurant not found by person id: $personId")
    }

    fun get(slug: String, response: Response<Any>): Response<Any> {
        val restaurant = restaurantRepository.findBySlug(slug)

        if (restaurant != null) {
            val h = openingHours(restaurant.deliveryTime)
            if (h.first) {
                response.data = RestaurantDTO(restaurant, OpeningHours.OPEN)
            } else {
                response.data = RestaurantDTO(restaurant, OpeningHours.CLOSED)
            }

            return response
        }

        throw RestaurantNotFoundException("Restaurant not found by slug: $slug")
    }

    private fun checkDataAvailability(name: String?, telephone: String?, legalPersonId: String?, result: BindingResult): BindingResult {
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

        legalPersonId?.let {
            if (restaurantRepository.existsByLegalPersonId(it)) {
                result.addError(ObjectError("restaurant", "Legal person id: $legalPersonId already use.")).let { log ->
                    logger.error(log.toString())
                }
                return result
            }
        }

        return result
    }
}