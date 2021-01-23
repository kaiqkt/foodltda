package com.foodltda.merchantservice.resouce.repositories

import com.foodltda.merchantservice.domain.entities.Restaurant
import com.foodltda.merchantservice.domain.entities.enums.Payment
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface RestaurantRepository : MongoRepository<Restaurant, String> {
    fun existsByName(name: String): Boolean
    fun existsByTelephone(telephone: String): Boolean
    fun existsBySlug(slug: String): Boolean
    fun findBySlug(slug: String): Restaurant?
    fun existsByOwnerCnpj(cnpj: String): Boolean
    fun findByOwnerId(id: String): Restaurant?
    fun findByFoodCategoryName(tag: String, pageable: Pageable): List<Restaurant>
    fun findByName(name: String, pageable: Pageable): List<Restaurant>
    fun findByPaymentMethods(payment: Payment, pageable: Pageable): List<Restaurant>
}