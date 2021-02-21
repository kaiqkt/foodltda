package singleregistry.application.controllers

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import singleregistry.domain.entities.Legal
import singleregistry.domain.services.LegalService

@RestController
@RequestMapping("/person")
class LegalController(private val legalService: LegalService) {

    @PostMapping
    fun register(@RequestBody legal: Legal) {
        legalService.create(legal)
    }
}