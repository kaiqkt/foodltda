package com.foodltda.merchantservice.application.dto.response

import com.foodltda.merchantservice.domain.entities.Products

data class ProductDTO (
        val category: String?,
        val products: MutableList<Products> = mutableListOf()
)
