package singleregistry.application.dto

import singleregistry.domain.entities.legal.Legal

data class LegalPersonResponse(
    val legal: Legal?
)