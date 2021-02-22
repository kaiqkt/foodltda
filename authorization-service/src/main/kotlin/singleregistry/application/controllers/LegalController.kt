package singleregistry.application.controllers

import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import singleregistry.application.dto.LegalRequest
import singleregistry.application.dto.toDomain
import singleregistry.application.validation.JsonValidator.validate
import singleregistry.domain.services.LegalService
import javax.validation.Valid

@RestController
@RequestMapping("/person")
class LegalController(private val legalService: LegalService) {

    @PostMapping
    fun register(@Valid @RequestBody legal: LegalRequest, result: BindingResult?) {
        validate(result)
        legalService.create(legal.toDomain())
    }
}