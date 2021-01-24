package com.foodltda.merchantservice.application.dto.request

import com.foodltda.merchantservice.domain.entities.Tag
import java.math.BigDecimal
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class ProductDTO (
        @get:NotEmpty(message = "Name cannot be empty")
        val name: String? = null,
        @get:NotEmpty(message = "Image cannot be empty")
        val image: String? = null,
        @get:NotNull(message = "price cannot be null")
        val price: BigDecimal? = null,
        @get:NotEmpty(message = "Description cannot be empty")
        val description: String? = null,
        @get:NotEmpty(message = "Tag cannot be empty")
        val tag: String? = null
)