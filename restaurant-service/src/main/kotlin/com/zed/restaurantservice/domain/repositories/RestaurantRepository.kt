package com.zed.restaurantservice.domain.repositories

import com.zed.restaurantservice.domain.entities.filter.Payment
import com.zed.restaurantservice.domain.entities.restaurant.Phone
import com.zed.restaurantservice.domain.entities.restaurant.Restaurant
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface RestaurantRepository : MongoRepository<Restaurant, String> {
    fun existsByPhone(phone: Phone): Boolean
    fun existsByPersonId(personId: String?): Boolean
    fun existsByName(name: String): Boolean
    fun findByPersonId(personId: String?): Restaurant?
    fun findBySlug(slug: String?): Restaurant?
    fun findAllByCategoryAndNameAndPayments(
        filter: String?,
        name: String?,
        payment: Payment?,
        pageable: Pageable
    ): List<Restaurant>
}