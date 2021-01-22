package com.foodltda.merchantservice

import com.foodltda.merchantservice.domain.entities.FoodCategory
import com.foodltda.merchantservice.resouce.repositories.FoodCategoryRespository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MerchantServiceApplication

fun main(args: Array<String>) {
	runApplication<MerchantServiceApplication>(*args)
}
