package com.zed.restaurantservice.domain.exceptions

class ResultBindingException(
    private val error: List<String>
) : DomainException() {
    override fun details() = error
}