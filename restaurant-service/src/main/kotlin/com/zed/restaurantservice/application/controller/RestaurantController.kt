package com.zed.restaurantservice.application.controller

import com.zed.restaurantservice.application.dto.RestaurantRequest
import com.zed.restaurantservice.application.dto.toDomain
import com.zed.restaurantservice.application.validation.JsonValidator
import com.zed.restaurantservice.domain.entities.restaurant.Restaurant
import com.zed.restaurantservice.domain.services.RestaurantService
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
        @RequestHeader("Authorization") token: String,
        result: BindingResult
    ): ResponseEntity<Restaurant> {
        JsonValidator.validate(result)

        return ResponseEntity<Restaurant>(
            restaurantService.create(restaurantRequest.toDomain(), token),
            HttpStatus.CREATED
        )
    }

}