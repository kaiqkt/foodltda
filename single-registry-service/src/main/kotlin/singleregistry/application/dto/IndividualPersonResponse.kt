package singleregistry.application.dto

import singleregistry.domain.entities.individual.Individual

data class IndividualPersonResponse(
    val individual: Individual?
)