package com.orderservice.paymentservice.resources.restaurantservice.client

import com.orderservice.paymentservice.resources.restaurantservice.entities.Response
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "restaurant-service", url = "http://localhost:8082")
interface RestaurantClient {

    @GetMapping("/restaurant/{restaurantId}")
    fun findByRestaurantId(@PathVariable restaurantId: String?, @RequestHeader("Authorization") accessToken: String): ResponseEntity<Response>
}