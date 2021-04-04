package com.zed.restaurantservice.application.controller

import com.zed.restaurantservice.application.dto.RestaurantRequest
import com.zed.restaurantservice.application.dto.toDomain
import com.zed.restaurantservice.application.validation.JsonValidator
import com.zed.restaurantservice.domain.entities.filter.Payment
import com.zed.restaurantservice.domain.entities.restaurant.Restaurant
import com.zed.restaurantservice.domain.entities.restaurant.RestaurantHours
import com.zed.restaurantservice.domain.services.RestaurantService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/restaurant")
class RestaurantController(val restaurantService: RestaurantService) {

    @PostMapping
    fun register(
        @Valid @RequestBody restaurantRequest: RestaurantRequest,
        @RequestHeader("Authorization") auth_token: String,
        result: BindingResult
    ): ResponseEntity<Restaurant> {
        JsonValidator.validate(result)

        return ResponseEntity<Restaurant>(
            restaurantService.create(restaurantRequest.toDomain(), auth_token),
            HttpStatus.CREATED
        )
    }

    @GetMapping
    fun current(@RequestHeader("Authorization") auth_token: String): ResponseEntity<Restaurant> {

        return ResponseEntity<Restaurant>(
            restaurantService.findByPersonId(auth_token),
            HttpStatus.OK
        )
    }

    @GetMapping("/{slug}")
    fun findBySlug(@PathVariable slug: String): ResponseEntity<Restaurant> {

        return ResponseEntity<Restaurant>(
            restaurantService.findBySlug(slug),
            HttpStatus.OK
        )
    }

    @GetMapping("/filter")
    fun filter(@RequestParam(defaultValue = "20") limit: Int,
               @RequestParam(defaultValue = "0") offset: Int,
               @RequestParam(defaultValue = "") category: String?,
               @RequestParam(defaultValue = "") name: String?,
               @RequestParam(defaultValue = "") payment: Payment?): ResponseEntity<List<RestaurantHours>> {

        val page = PageRequest.of(offset, limit, Sort.Direction.DESC, "name")

        return ResponseEntity<List<RestaurantHours>>(
            restaurantService.findRestaurants(category, name, payment, page),
            HttpStatus.OK
        )
    }
}