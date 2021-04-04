package com.zed.restaurantservice.domain.services

import com.github.slugify.Slugify
import com.zed.restaurantservice.domain.entities.filter.Payment
import com.zed.restaurantservice.domain.entities.restaurant.Restaurant
import com.zed.restaurantservice.domain.entities.restaurant.RestaurantHours
import com.zed.restaurantservice.domain.exceptions.DataValidationException
import com.zed.restaurantservice.domain.exceptions.RestaurantFilterNotFoundException
import com.zed.restaurantservice.domain.repositories.RestaurantFilterRepository
import com.zed.restaurantservice.domain.repositories.RestaurantRepository
import com.zed.restaurantservice.resources.security.JWTUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalTime

@Service
class RestaurantService(
    private val restaurantRepository: RestaurantRepository,
    private val restaurantFilterRepository: RestaurantFilterRepository,
    private val jwtUtil: JWTUtil
) {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(RestaurantService::class.java.name)
    }

    fun create(restaurant: Restaurant, token: String): Restaurant {
        validateDate(restaurant)

        restaurantFilterRepository.findByName(restaurant.category)
            ?: throw RestaurantFilterNotFoundException("Filter: $restaurant.restaurantFilter not found")

        val personId = jwtUtil.getPersonId(token.substring(7))

        val newRestaurant = restaurant.copy(
            personId = personId,
            slug = Slugify().slugify(restaurant.name)
        )

        restaurantRepository.save(newRestaurant).also {
            logger.info("Restaurant[${it._id}] created ")
            return it
        }
    }

    fun findByPersonId(token: String): Restaurant? {
        val personId = jwtUtil.getPersonId(token.substring(7))

        return restaurantRepository.findByPersonId(personId)
    }

    fun findRestaurants(category: String?, name: String?, payment: Payment?, page: PageRequest): List<RestaurantHours> {
        val restaurants = restaurantRepository.findAllByCategoryAndNameAndPayments(
            category,
            name,
            payment,
            page
        )

        return getOpenedRestaurants(restaurants)
    }

    fun findBySlug(slug: String) = restaurantRepository.findBySlug(slug)

    private fun getOpenedRestaurants(restaurants: List<Restaurant>): List<RestaurantHours> {
        val restaurantsResponse = mutableListOf<RestaurantHours>()

        restaurants.map { restaurant ->
            restaurant.openingHours.map {
                if (LocalDate.now().dayOfWeek == it.dayOfWeek) {
                    if (LocalTime.now().isBefore(LocalTime.parse(it.closeAt)) && LocalTime.now()
                            .isAfter(LocalTime.parse(it.openAt))
                    ) {
                        val response = RestaurantHours(
                            restaurant = restaurant,
                            openAt = it.openAt,
                            closeAt = it.closeAt,
                            closed = false)

                        restaurantsResponse.add(response)
                    }
                    val response = RestaurantHours(
                        restaurant = restaurant,
                        openAt = it.openAt,
                        closeAt = it.closeAt,
                    )

                    restaurantsResponse.add(response)

                }
            }
        }

        return restaurantsResponse.sortedBy { !it.closed }
    }

    private fun validateDate(restaurant: Restaurant) {
        val error = mutableListOf<String>()

        restaurant.personId.let {
            if (restaurantRepository.existsByPersonId(it)) {
                error.add("PersonId: $it already use")
            }
        }

        restaurant.name.let {
            if (restaurantRepository.existsByName(it)) {
                error.add("Restaurant name: $it already use")
            }
        }

        restaurant.phone.let {
            if (restaurantRepository.existsByPhone(it)) {
                error.add("Phone: ${it.countryCode} + ${it.areaCode} + ${it.number} already use")
            }
        }

        if (error.isNotEmpty()) {
            throw DataValidationException(error)
        }
    }
}