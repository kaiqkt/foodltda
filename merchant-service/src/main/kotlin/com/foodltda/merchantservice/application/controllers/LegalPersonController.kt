package com.foodltda.merchantservice.application.controllers

import com.foodltda.merchantservice.application.dto.request.PersonRegistrationDTO
import com.foodltda.merchantservice.application.dto.request.UpdatePerson
import com.foodltda.merchantservice.application.dto.response.Response
import com.foodltda.merchantservice.domain.service.LegalPersonService
import com.foodltda.merchantservice.resouce.repositories.LegalPersonRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/person")
class LegalPersonController(val legalPersonService: LegalPersonService, val legalPersonRepository: LegalPersonRepository) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody personRegistrationDTO: PersonRegistrationDTO, result: BindingResult): ResponseEntity<Any> {
        val response = Response<Any>()
        legalPersonService.register(personRegistrationDTO, response, result)

        return ResponseEntity(HttpStatus.CREATED)
    }

    @PutMapping("/update/{personId}")
    fun update(@PathVariable personId: String, @Valid @RequestBody person: UpdatePerson, result: BindingResult): ResponseEntity<Any> {
        val response = Response<Any>()
        val update = legalPersonService.update(personId, person, response, result)

        return ResponseEntity.ok(update.data as Any)
    }

    @GetMapping("/current/{personId}")
    fun profile(@PathVariable personId: String){

    }
}