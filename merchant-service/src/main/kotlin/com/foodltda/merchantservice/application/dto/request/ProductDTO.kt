package com.foodltda.merchantservice.application.dto.request

import com.foodltda.merchantservice.application.dto.Tag
import java.math.BigDecimal

data class ProductDTO (
        val name: String? = null,
        val image: String? = null,
        val price: BigDecimal? = null,
        val description: String? = null,
        val tag: Tag?
)