package com.zed.restaurantservice.domain.services

import com.zed.restaurantservice.domain.entities.restaurant.ProductCategory
import com.zed.restaurantservice.domain.exceptions.DataValidationException
import com.zed.restaurantservice.domain.exceptions.RestaurantNotFoundException
import com.zed.restaurantservice.domain.repositories.ProductCategoryRepository
import com.zed.restaurantservice.domain.repositories.RestaurantRepository
import com.zed.restaurantservice.resources.security.JWTUtil
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ProductCategoryService(
    private val productCategoryRepository: ProductCategoryRepository,
    private val restaurantRepository: RestaurantRepository,
    private val jwtUtil: JWTUtil
) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun create(productCategory: ProductCategory, token: String): ProductCategory {
        val personId = jwtUtil.getPersonId(token.substring(7))

        val restaurant = restaurantRepository.findByPersonId(personId)
            ?: throw RestaurantNotFoundException("Restaurant not found by person id: $personId")

        return productCategoryRepository.findByName(productCategory.name)
            ?: productCategoryRepository.save(productCategory.copy(restaurantId = restaurant._id)).also {
                restaurant.productsCategories.add(it)
                restaurantRepository.save(restaurant)

                log.info("Product Category[${it._id}] created ")
                return it
            }
    }

    fun findAllByRestaurantId(restaurantId: String) = productCategoryRepository.findAllByRestaurantId(restaurantId)
}