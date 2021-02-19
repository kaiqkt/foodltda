package com.foodltda.merchantservice.domain.service

import com.foodltda.merchantservice.domain.entities.Tag
import com.foodltda.merchantservice.domain.exceptions.ProductAlreadyExistException
import com.foodltda.merchantservice.domain.repositories.ProductsRepository
import com.foodltda.merchantservice.domain.repositories.TagRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TagService(val tagRepository: TagRepository, val productsRepository: ProductsRepository) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(TagService::class.java.name)
    }

    fun find(name: String?, restaurantId: String?) = tagRepository.findByNameAndRestaurantId(name, restaurantId)

    fun create(name: String, restaurantId: String?) =
        tagRepository.findByNameAndRestaurantId(name, restaurantId)
            .takeIf { it == null } ?: tagRepository.save(Tag(name = name, restaurantId = restaurantId)).also { logger.info("Create product: ${it.id}") }


    fun delete(name: String, restaurantId: String) {
        val product = productsRepository.findByTagName(name, restaurantId)

        if (product != null) {
            tagRepository.deleteByName(name)
        } else  {
            throw  ProductAlreadyExistException("Cannot be delete tag: $name, why is used in ${product?.id}")
        }
    }
}