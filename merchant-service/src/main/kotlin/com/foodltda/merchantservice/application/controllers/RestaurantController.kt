package com.foodltda.merchantservice.application.controllers

import com.foodltda.merchantservice.application.dto.request.RestaurantRegistrationDTO
import com.foodltda.merchantservice.application.dto.response.Response
import com.foodltda.merchantservice.domain.service.RestaurantService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/restaurant")
class RestaurantController(val restaurantService: RestaurantService) {

    @PostMapping("/register/{personId}")
    fun register(@PathVariable personId: String, @Valid @RequestBody restaurantRegistrationDTO: RestaurantRegistrationDTO, result: BindingResult): ResponseEntity<HttpStatus> {
        val response = Response<Any>()
        restaurantService.register(personId, restaurantRegistrationDTO, response, result)

        return ResponseEntity(HttpStatus.CREATED)
    }

    @PutMapping("/update/{restaurantId}")
    fun update(){}
}