package com.zed.restaurantservice.domain.services

import com.github.slugify.Slugify
import com.zed.restaurantservice.domain.entities.filter.Payment
import com.zed.restaurantservice.domain.entities.restaurant.Restaurant
import com.zed.restaurantservice.domain.entities.restaurant.RestaurantHours
import com.zed.restaurantservice.domain.exceptions.DataValidationException
import com.zed.restaurantservice.domain.exceptions.CategoryNotFoundException
import com.zed.restaurantservice.domain.exceptions.NotLegalPersonException
import com.zed.restaurantservice.domain.repositories.CategoryRepository
import com.zed.restaurantservice.domain.repositories.RestaurantRepository
import com.zed.restaurantservice.resources.security.JWTUtil
import com.zed.restaurantservice.resources.singleregistry.gateways.SingleRegistryImpl
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalTime

@Service
class RestaurantService(
    private val restaurantRepository: RestaurantRepository,
    private val categoryRepository: CategoryRepository,
    private val singleRegistryImpl: SingleRegistryImpl,
    private val jwtUtil: JWTUtil
) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun create(restaurant: Restaurant, token: String): Restaurant {
        val personId = jwtUtil.getPersonId(token.substring(7))

        if (!singleRegistryImpl.isLegalPerson(personId)){
            throw NotLegalPersonException("Person $personId is not valid for create a new restaurant")
        }

        val newRestaurant = restaurant.copy(
            personId = personId,
            slug = Slugify().slugify(restaurant.name)
        )

        validateDate(newRestaurant)

        categoryRepository.findByName(restaurant.category)
            ?: throw CategoryNotFoundException("Restaurant category ${restaurant.category} not found")

        restaurantRepository.save(newRestaurant).also {
            log.info("Restaurant[${it._id}] created ")
            return it
        }
    }

    fun findByPersonId(token: String): Restaurant? {
        val personId = jwtUtil.getPersonId(token.substring(7))

        return restaurantRepository.findByPersonId(personId)
    }

    fun findRestaurants(category: String?, name: String?, payment: Payment?, page: PageRequest): List<RestaurantHours> {
        val filters = category.isNullOrBlank() && name.isNullOrBlank() && payment == null

        val restaurants = if (!filters) {
            restaurantRepository.findAllByCategoryOrNameOrPayments(
                category,
                name,
                payment,
                page
            )
        } else {
            restaurantRepository.findAll(page).toList()
        }


        return getOpenedRestaurants(restaurants)
    }

    fun findBySlug(slug: String) = restaurantRepository.findBySlug(slug)

    private fun getOpenedRestaurants(restaurants: List<Restaurant>): List<RestaurantHours> {
        val restaurantsResponse = mutableListOf<RestaurantHours>()

        restaurants.map { restaurant ->
            restaurant.openingHours.map {
                if (LocalDate.now().dayOfWeek == it.dayOfWeek) {
                    val openAt = LocalTime.parse(it.openAt)
                    var closeAt = LocalTime.parse(it.closeAt)

                    if (it.closeAt == "00:00:00") {
                        closeAt = LocalTime.parse("23:59")
                    }

                    if (LocalTime.now().isAfter(openAt) && LocalTime.now()
                            .isBefore(closeAt)
                    ) {
                        val response = RestaurantHours(
                            restaurant = restaurant,
                            openAt = it.openAt,
                            closeAt = it.closeAt,
                            closed = false
                        )

                        restaurantsResponse.add(response)
                    } else {
                        val response = RestaurantHours(
                            restaurant = restaurant,
                            openAt = it.openAt,
                            closeAt = it.closeAt,
                        )

                        restaurantsResponse.add(response)
                    }
                }
            }
        }
        return restaurantsResponse.sortedBy { it.closed }
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
                error.add("Phone: ${it.countryCode}${it.areaCode}${it.number} already use")
            }
        }

        if (error.isNotEmpty()) {
            throw DataValidationException(error)
        }
    }
}