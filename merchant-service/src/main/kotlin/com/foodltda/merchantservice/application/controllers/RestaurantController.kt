package com.foodltda.merchantservice.application.controllers

import com.foodltda.merchantservice.application.dto.request.RestaurantRegistrationDTO
import com.foodltda.merchantservice.application.dto.request.UpdateRestaurant
import com.foodltda.merchantservice.application.dto.response.Response
import com.foodltda.merchantservice.domain.entities.enums.Payment
import com.foodltda.merchantservice.domain.service.RestaurantService
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

    @PostMapping("/register/{personId}")
    fun register(@PathVariable personId: String, @Valid @RequestBody restaurantRegistrationDTO: RestaurantRegistrationDTO, result: BindingResult): ResponseEntity<Any> {
        val response = Response<Any>()
        restaurantService.register(personId, restaurantRegistrationDTO, response, result)

        return ResponseEntity.ok(response)
    }

    @PutMapping("/update/{slug}/{personId}")
    fun update(@PathVariable slug: String, @PathVariable personId: String, @RequestBody restaurant: UpdateRestaurant, result: BindingResult): ResponseEntity<Any>{
        val response = Response<Any>()
        restaurantService.update(slug, personId, restaurant, response, result)

        return ResponseEntity.ok(response)
    }

    //alterar
    @GetMapping("/current/{personId}")
    fun current(@PathVariable personId: String): ResponseEntity<Any> {
        val response =  Response<Any>()
        restaurantService.getRestaurantByPersonId(personId, response)

        return ResponseEntity.ok(response)
    }

    @GetMapping("/get/{slug}")
    fun getRestaurant(@PathVariable slug: String): ResponseEntity<Any> {
        val response =  Response<Any>()
        restaurantService.get(slug, response)

        return ResponseEntity.ok(response)
    }

    @GetMapping("/filter")
    fun filter(@RequestParam(defaultValue = "20") limit: Int,
                 @RequestParam(defaultValue = "0") offset: Int,
                 @RequestParam(defaultValue = "") tag: String,
                 @RequestParam(defaultValue = "") name: String,
                @RequestParam(defaultValue = "") payment: Payment?): ResponseEntity<Any> {

        val response = Response<Any>()
        val page = PageRequest.of(offset, limit, Sort.Direction.DESC, "name")

        restaurantService.getBy(tag, name, payment, page, response)

        return ResponseEntity.ok(response)
    }
}