package com.zed.restaurantservice.factories

import com.zed.restaurantservice.application.dto.ProductRequest
import java.math.BigDecimal

object ProductRequestFactory {

    fun sample() = ProductRequest(
        name = "product-request-sample",
        image = "image-sample",
        price = BigDecimal(50.60),
        description = "description-sample",
        foodCategory = "category-sample"
    )
}