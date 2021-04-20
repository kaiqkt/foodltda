package com.zed.restaurantservice.factories

import com.zed.restaurantservice.application.dto.toAddress
import com.zed.restaurantservice.application.dto.toPhone
import com.zed.restaurantservice.domain.entities.filter.Payment
import com.zed.restaurantservice.domain.entities.restaurant.DeliveryTime
import com.zed.restaurantservice.domain.entities.restaurant.Restaurant
import java.time.DayOfWeek

object RestaurantFactory {

    fun sample() = Restaurant(
        name = "restaurant",
        image = "image",
        address = RestaurantRequestFactory.sample().toAddress(),
        phone =  RestaurantRequestFactory.sample().toPhone(),
        openingHours = mutableListOf(
            DeliveryTime(
                DayOfWeek.SUNDAY,
                "09:00:00",
                "23:00:00"
            )
        ),
        category = "filter",
        payments = mutableListOf(Payment.PAG_SEGURO)
    )
}