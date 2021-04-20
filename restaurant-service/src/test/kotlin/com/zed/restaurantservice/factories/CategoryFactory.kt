package com.zed.restaurantservice.factories

import com.zed.restaurantservice.domain.entities.filter.Category

object CategoryFactory {

    fun sample() = Category(
        _id = "123142",
        name = "category-sample",
        image = "image-sample"
    )
}