package com.zed.restaurantservice.factories

import com.zed.restaurantservice.application.dto.RestaurantRequest
import com.zed.restaurantservice.domain.entities.filter.Payment
import com.zed.restaurantservice.domain.entities.restaurant.DeliveryTime
import java.time.DayOfWeek

object RestaurantRequestFactory {

    fun sample() = RestaurantRequest(
        name = "restaurant request",
        image = "12314214",
        delivery = mutableListOf(
            DeliveryTime(
                DayOfWeek.SUNDAY,
                "09:00:00",
                "23:00:00"
            )
        ),
        restaurantFilter = "filter",
        payment = mutableListOf(Payment.PAG_SEGURO),
        street = "street request",
        number = "70",
        complement = "",
        district = "district",
        city = "city request",
        state = "state request",
        country = "country request",
        postalCode = "2132141",
        countryCode = "55",
        areaCode = "11",
        phoneNumber = "1548411561"
    )
}