package com.foodltda.merchantservice.application.dto.request

import java.math.BigDecimal

data class UpdateProduct (
        val name: String? = null,
        val image: String? = null,
        val price: BigDecimal? = null,
        val description: String? = null,
        val tag: String? = null
)