package com.zed.restaurantservice.domain.services

import com.github.slugify.Slugify
import com.zed.restaurantservice.domain.entities.restaurant.Product
import com.zed.restaurantservice.domain.exceptions.CategoryNotFoundException
import com.zed.restaurantservice.domain.exceptions.RestaurantNotFoundException
import com.zed.restaurantservice.domain.repositories.ProductCategoryRepository
import com.zed.restaurantservice.domain.repositories.ProductsRepository
import com.zed.restaurantservice.domain.repositories.RestaurantRepository
import com.zed.restaurantservice.resources.security.JWTUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductService(
    private val productRepository: ProductsRepository,
    private val productCategoryRepository: ProductCategoryRepository,
    private val restaurantRepository: RestaurantRepository,
    private val jwtUtil: JWTUtil
) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(ProductService::class.java.name)
    }

    fun create(product: Product, token: String): Product {
        val personId = jwtUtil.getPersonId(token.substring(7))

        val restaurant = restaurantRepository.findByPersonId(personId)
            ?: throw RestaurantNotFoundException("Restaurant not found by person id: $personId")

        productCategoryRepository.findByName(product.foodCategory)
            ?: throw CategoryNotFoundException("Food category ${product.foodCategory} not found")

        var slug = Slugify().slugify(product.name)

        if (productRepository.existsBySlug(slug)) {
            slug += "-" + UUID.randomUUID().toString().substring(0, 8)
        }

        productRepository.save(product.copy(slug = slug, restaurantId = restaurant._id)).also {
            restaurant.products.add(it)
            restaurantRepository.save(restaurant)

            logger.info("Product[${it._id}] created ")
            return it
        }
    }

    fun findBySlugAndRestaurantId(slug: String?, restaurantId: String?) =
        productRepository.findBySlugAndRestaurantId(slug, restaurantId)

    fun findAllByCategory(category: String?, restaurantId: String?) =
        productRepository.findAllByFoodCategoryAndRestaurantId(category, restaurantId)

    fun findAll(restaurantId: String): List<Product> {
        val products = productRepository.findAllByRestaurantId(restaurantId)

        return products.sortedBy { it.foodCategory }
    }
}