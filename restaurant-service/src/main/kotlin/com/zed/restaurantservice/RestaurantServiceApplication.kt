package com.zed.restaurantservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RestaurantServiceApplication

fun main(args: Array<String>) {
	runApplication<RestaurantServiceApplication>(*args)
}
