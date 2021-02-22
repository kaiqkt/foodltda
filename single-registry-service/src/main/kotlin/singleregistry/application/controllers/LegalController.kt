package singleregistry.application.controllers

import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import singleregistry.application.dto.LegalRequest
import singleregistry.application.dto.toDomain
import singleregistry.application.validation.JsonValidator.validate
import singleregistry.domain.entities.legal.Legal
import singleregistry.domain.services.LegalService
import javax.validation.Valid

@RestController
@RequestMapping("/legals")
class LegalController(private val legalService: LegalService) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun register(@Valid @RequestBody legal: LegalRequest, result: BindingResult): Legal {
        validate(result)
        return legalService.create(legal.toDomain())
    }
}