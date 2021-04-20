package com.zed.restaurantservice.factories

import com.zed.restaurantservice.application.dto.ProductCategoryRequest

object ProductCategoryRequestFactory {

    fun sample() = ProductCategoryRequest(
        name = "product-category-request"
    )
}