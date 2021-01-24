package com.foodltda.merchantservice.domain.service

import com.foodltda.merchantservice.application.dto.request.ProductDTO
import com.foodltda.merchantservice.application.dto.response.Response
import com.foodltda.merchantservice.domain.entities.Products
import com.foodltda.merchantservice.domain.entities.Restaurant
import com.foodltda.merchantservice.domain.exceptions.TagNotFoundException
import com.foodltda.merchantservice.resouce.repositories.ProductsRepository
import com.foodltda.merchantservice.resouce.repositories.RestaurantRepository
import com.foodltda.merchantservice.resouce.repositories.TagRepository
import com.github.slugify.Slugify
import org.springframework.stereotype.Service
import org.springframework.validation.BindingResult
import java.util.*

@Service
class ProductService(val productsRepository: ProductsRepository,
                     val restaurantRepository: RestaurantRepository,
                     val restaurantService: RestaurantService,
                     val tagRepository: TagRepository) {

    fun register(personId: String, productDTO: ProductDTO, result: BindingResult, response: Response<Any>): Response<Any> {
        val restaurant = restaurantService.getRestaurantByPersonId(personId, response).data as Restaurant

        var slug = Slugify().slugify(productDTO.name)

        if (productsRepository.existsBySlug(slug)) {
            slug += "-" + UUID.randomUUID().toString().substring(0, 8)
        }

        val tag = productDTO.tag.let {
            tagRepository.findByName(it) ?: throw TagNotFoundException("Tag: $it not be exist")
        }

        response.data = productsRepository.save(Products.fromDocument(productDTO, slug, restaurant.id, tag)).let {
            restaurant.products.add(it.slug)
            restaurantRepository.save(restaurant)
        }

        return response
    }
}
