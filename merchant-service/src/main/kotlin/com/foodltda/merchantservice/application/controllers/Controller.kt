package com.foodltda.merchantservice.application.controllers

import com.foodltda.merchantservice.domain.exceptions.CityNotFoundException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/ex")
class Controller {

    @GetMapping()
    fun a(): String {
        throw CityNotFoundException(5)
    }
}