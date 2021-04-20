package com.zed.restaurantservice.factories

import com.zed.restaurantservice.domain.entities.restaurant.Product
import java.math.BigDecimal

object ProductFactory {
    fun sample() = Product(
        slug = "slug-sample",
        name = "name-sample",
        image = "image-sample",
        price = BigDecimal(50.66),
        description = "description sample",
        foodCategory = "food category"
    )
}