package com.zed.restaurantservice.domain.services

import com.github.slugify.Slugify
import com.zed.restaurantservice.domain.entities.restaurant.Restaurant
import com.zed.restaurantservice.domain.exceptions.DataValidationException
import com.zed.restaurantservice.domain.exceptions.RestaurantFilterNotFoundException
import com.zed.restaurantservice.domain.repositories.RestaurantFilterRepository
import com.zed.restaurantservice.domain.repositories.RestaurantRepository
import com.zed.restaurantservice.resources.security.JWTUtil
import com.zed.restaurantservice.resources.singleregistry.entities.Legal
import com.zed.restaurantservice.resources.singleregistry.gateways.SingleRegistryServiceImpl
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class RestaurantService(
    private val restaurantRepository: RestaurantRepository,
    private val restaurantFilterRepository: RestaurantFilterRepository,
    private val singleRegistryServiceImpl: SingleRegistryServiceImpl,
    private val jwtUtil: JWTUtil
) {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(RestaurantService::class.java.name)
    }

    fun create(restaurant: Restaurant, token: String): Restaurant {
        validateDate(restaurant)

        restaurantFilterRepository.findByName(restaurant.restaurantFilter)
            ?: throw RestaurantFilterNotFoundException("Filter: $restaurant.restaurantFilter not found")

        var slug = Slugify().slugify(restaurant.name)

        if (restaurantRepository.existsBySlug(slug)) {
            slug += "-" + UUID.randomUUID().toString().substring(0, 8)
        }

        val personId = jwtUtil.getPersonId(token)
        val legal = singleRegistryServiceImpl.findByPersonId(personId)

        val newRestaurant = restaurant.copy(
            personId = personId,
            cnpj = legal?.cnpj,
            slug = slug
        )

        restaurantRepository.save(newRestaurant).also {
            logger.info("Restaurant[${it._id}] created ")
            return it
        }
    }

    private fun validateDate(restaurant: Restaurant) {
        val error = mutableListOf<String>()

        restaurant.cnpj.let {
            if (restaurantRepository.existsByCnpj(it)) {
                error.add("CNPJ: $it already use")
            }
        }
        restaurant.phone.let {
            if (restaurantRepository.existsByPhone(it)) {
                error.add("Phone: ${it.countryCode} + it.areaCode + ${it.number} already use")
            }
        }

        if (error.isNotEmpty()) {
            throw DataValidationException(error)
        }
    }
}