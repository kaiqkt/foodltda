package com.foodltda.merchantservice.application.controllers

import com.foodltda.merchantservice.domain.service.TagService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tags")
class TagController(val tagService: TagService) {

    @PostMapping("/register/{name}/{restaurantId}")
    fun register(@PathVariable name: String, @PathVariable restaurantId: String, result: BindingResult): ResponseEntity<Any> {
        tagService.create(name, restaurantId)

        return ResponseEntity(HttpStatus.CREATED)
    }

    @PostMapping("/register/{name}/{restaurantId}")
    fun delete(@PathVariable name: String, @PathVariable restaurantId: String, result: BindingResult): ResponseEntity<Any> {
        tagService.delete(name, restaurantId)

        return ResponseEntity(HttpStatus.OK)
    }
}