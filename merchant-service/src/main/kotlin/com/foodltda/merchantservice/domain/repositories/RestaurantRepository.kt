package com.foodltda.merchantservice.domain.repositories

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
    fun existsByLegalPersonId(cnpj: String): Boolean
    fun findByLegalPersonId(id: String): Restaurant?
    fun findByRestaurantCategoryName(tag: String, pageable: Pageable): List<Restaurant>
    fun findByName(name: String, pageable: Pageable): List<Restaurant>
    fun findByPaymentMethods(payment: Payment, pageable: Pageable): List<Restaurant>
}