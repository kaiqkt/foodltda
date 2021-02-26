package singleregistry.application.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import singleregistry.application.dto.LegalRequest
import singleregistry.application.dto.toDomain
import singleregistry.application.validation.JsonValidator.validate
import singleregistry.domain.entities.legal.Legal
import singleregistry.domain.services.LegalService
import javax.validation.Valid

@RestController
@RequestMapping("/legals")
class LegalController(private val legalService: LegalService) {

    // MediaType.APPLICATION_JSON_VALUE] produces={"application/json","application/xml"} rem_legal_v1
    @PostMapping()
    fun register(@Valid @RequestBody legal: LegalRequest, result: BindingResult): ResponseEntity<Legal> {
        validate(result)

        return ResponseEntity<Legal>(legalService.create(legal.toDomain()), HttpStatus.CREATED)
    }
}