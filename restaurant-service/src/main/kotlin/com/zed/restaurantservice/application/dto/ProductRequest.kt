package com.zed.restaurantservice.application.dto

import com.zed.restaurantservice.domain.entities.restaurant.Product
import java.math.BigDecimal
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class ProductRequest(
    @NotEmpty(message = "name cannot be empty")
    val name: String = "",
    @NotEmpty(message = "image cannot be empty")
    val image: String = "",
    @NotNull(message = "price cannot be empty")
    val price: BigDecimal? = null,
    val description: String = "",
    @NotEmpty(message = "foodCategory cannot be empty")
    val foodCategory: String = "",
)

fun ProductRequest.toDomain() = Product(
    name = this.name,
    image = this.image,
    price = this.price,
    description = this.description,
    foodCategory = this.foodCategory
)