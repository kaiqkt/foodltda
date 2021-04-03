package singleregistry.application.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import singleregistry.application.dto.IndividualRequest
import singleregistry.application.dto.toDomain
import singleregistry.application.validation.JsonValidator
import singleregistry.domain.entities.individual.Individual
import singleregistry.domain.services.IndividualService
import javax.validation.Valid

@RestController
@RequestMapping("/individual")
class IndividualController(private val individualService: IndividualService) {

    @PostMapping
    fun register(@Valid @RequestBody individual: IndividualRequest, result: BindingResult): ResponseEntity<Individual> {
        JsonValidator.validate(result)

        return ResponseEntity<Individual>(
            individualService.create(individual.toDomain(), individual.password),
            HttpStatus.CREATED
        )
    }

    @GetMapping("/{cpf}")
    fun getByCpf(@PathVariable cpf: String): ResponseEntity<Individual> {

        return ResponseEntity<Individual>(individualService.findByCpf(cpf), HttpStatus.ACCEPTED)
    }
}