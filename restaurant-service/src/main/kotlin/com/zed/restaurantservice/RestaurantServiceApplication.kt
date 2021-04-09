package com.zed.restaurantservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication
class RestaurantServiceApplication


fun main(args: Array<String>) {
    runApplication<RestaurantServiceApplication>(*args)
}
