package com.foodltda.merchantservice.application.dto.request

import com.foodltda.merchantservice.application.dto.Tag
import java.math.BigDecimal

data class ProductDTO (
        val name: String,
        val image: String?,
        val price: BigDecimal,
        val description: String?,
        val tag: Tag
)