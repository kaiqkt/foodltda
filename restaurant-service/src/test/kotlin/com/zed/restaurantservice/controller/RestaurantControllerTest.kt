package com.zed.restaurantservice.controller

import com.zed.restaurantservice.application.controller.RestaurantController
import com.zed.restaurantservice.application.dto.toDomain
import com.zed.restaurantservice.domain.services.RestaurantService
import com.zed.restaurantservice.factories.RestaurantFactory
import com.zed.restaurantservice.factories.RestaurantRequestFactory
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import java.util.*

class RestaurantControllerTest {
    private lateinit var restaurantService: RestaurantService
    private lateinit var restaurantController: RestaurantController
    private lateinit var result: BindingResult

    @BeforeEach
    fun beforeEach() {
        restaurantService = mockk(relaxed = true)
        restaurantController = RestaurantController(restaurantService)
        result = mockk(relaxed = true)
    }

    @Test
    fun `given valid restaurant request should return restaurant and http status 201`() {
        val request = RestaurantRequestFactory.sample()
        val token = UUID.randomUUID().toString()
        val restaurant = RestaurantFactory.sample()

            every { restaurantService.create(request.toDomain(), token) } returns restaurant

        val controller = restaurantController.register(request, token, result)

        verify { restaurantService.create(any(), any()) }
        Assertions.assertEquals(HttpStatus.CREATED, controller.statusCode)
    }

    @Test
    fun `given valid token should return restaurant and http status 201`() {
        val token = UUID.randomUUID().toString()
        val restaurant = RestaurantFactory.sample()

        every { restaurantService.findByPersonId(token) } returns restaurant

        val controller = restaurantController.current(token)

        verify { restaurantService.findByPersonId(any()) }
        Assertions.assertEquals(HttpStatus.OK, controller.statusCode)
    }
}