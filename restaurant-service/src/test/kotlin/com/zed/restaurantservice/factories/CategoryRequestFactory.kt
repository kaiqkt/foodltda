package com.zed.restaurantservice.factories

import com.zed.restaurantservice.application.dto.CategoryRequest

object CategoryRequestFactory {

    fun sample() = CategoryRequest(
        name = "Category-sample",
        image = "image-sample"
    )
}