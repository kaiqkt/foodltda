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
import javax.websocket.server.PathParam

@RestController
@RequestMapping("/legal")
class LegalController(private val legalService: LegalService) {

    // MediaType.APPLICATION_JSON_VALUE] produces={"application/json","application/xml"} rem_legal_v1
    @PostMapping
    fun register(@Valid @RequestBody legal: LegalRequest, result: BindingResult): ResponseEntity<Legal> {
        validate(result)

        return ResponseEntity<Legal>(legalService.create(legal.toDomain()), HttpStatus.CREATED)
    }

    //service token
    @GetMapping("/{cnpj}")
    fun getByCnpj(@PathVariable cnpj: String): ResponseEntity<Legal> {

        return ResponseEntity<Legal>(legalService.findByCnpj(cnpj), HttpStatus.ACCEPTED)
    }

    //update?
}