package singleregistry.application.dto

import singleregistry.domain.entities.individual.Individual
import singleregistry.domain.entities.legal.Legal

data class PersonResponse(
    val legal: Legal? = null,
    val individual: Individual? = null
)