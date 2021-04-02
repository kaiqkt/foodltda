package singleregistry.application.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import singleregistry.application.dto.LegalRequest
import singleregistry.application.dto.toDomain
import singleregistry.application.validation.JsonValidator.validate
import singleregistry.domain.entities.legal.Legal
import singleregistry.domain.services.LegalService
import javax.validation.Valid

@RestController
@RequestMapping("/legal")
class LegalController(private val legalService: LegalService) {

    @PostMapping
    fun register(@Valid @RequestBody legal: LegalRequest, result: BindingResult): ResponseEntity<Legal> {
        validate(result)

        return ResponseEntity<Legal>(legalService.create(legal.toDomain(), legal.password), HttpStatus.CREATED)
    }

    @GetMapping("/{cnpj}")
    fun getByCnpj(@PathVariable cnpj: String): ResponseEntity<Legal> {

        return ResponseEntity<Legal>(legalService.findByCnpj(cnpj), HttpStatus.ACCEPTED)
    }

}