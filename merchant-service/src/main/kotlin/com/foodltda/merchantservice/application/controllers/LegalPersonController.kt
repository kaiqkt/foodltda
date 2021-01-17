package com.foodltda.merchantservice.application.controllers

import com.foodltda.merchantservice.application.dto.request.PersonRegistrationDTO
import com.foodltda.merchantservice.application.dto.response.Response
import com.foodltda.merchantservice.domain.service.LegalPersonService
import com.foodltda.merchantservice.resouce.repositories.LegalPersonRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/user")
class LegalPersonController(val legalPersonService: LegalPersonService, val legalPersonRepository: LegalPersonRepository) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody personRegistrationDTO: PersonRegistrationDTO, result: BindingResult): ResponseEntity<Any> {
        val response = Response<Any>()
        legalPersonService.register(personRegistrationDTO, response, result)

        return ResponseEntity(HttpStatus.CREATED)
    }

}