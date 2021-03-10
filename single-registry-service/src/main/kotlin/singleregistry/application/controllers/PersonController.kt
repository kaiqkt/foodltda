package singleregistry.application.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import singleregistry.application.dto.PersonResponse
import singleregistry.domain.entities.person.Person
import singleregistry.domain.services.PersonService

@RestController
@RequestMapping("/person")
class PersonController(private val personService: PersonService){

    //alterar
    //service token
    @GetMapping("/{personId}")
    fun getByPersonId(@PathVariable personId: String): ResponseEntity<PersonResponse> {

        return ResponseEntity<PersonResponse>(personService.findByPersonId(personId), HttpStatus.ACCEPTED)
    }

}