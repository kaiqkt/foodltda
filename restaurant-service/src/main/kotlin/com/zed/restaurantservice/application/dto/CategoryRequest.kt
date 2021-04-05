package com.zed.restaurantservice.application.dto

import com.zed.restaurantservice.domain.entities.filter.Category
import javax.validation.constraints.NotEmpty

data class CategoryRequest (
    @get:NotEmpty(message = "Name cannot be empty.")
    val name: String = "",
    @get:NotEmpty(message = "Image cannot be empty.")
    val image: String = ""
)

fun CategoryRequest.toDomain() = Category(
    name = this.name,
    image = this.image
)