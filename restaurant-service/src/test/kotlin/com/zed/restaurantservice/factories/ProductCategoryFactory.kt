package com.zed.restaurantservice.factories

import com.zed.restaurantservice.domain.entities.restaurant.ProductCategory

object ProductCategoryFactory {

    fun sample() = ProductCategory(
        name = "product-category"
    )
}