package com.zed.restaurantservice.application.dto

import com.zed.restaurantservice.domain.entities.restaurant.ProductCategory
import javax.validation.constraints.NotEmpty

data class ProductCategoryRequest(
    @get:NotEmpty(message = "Name cannot be empty.")
    val name: String = ""
)

fun ProductCategoryRequest.toDomain() = ProductCategory(
    name = this.name
)