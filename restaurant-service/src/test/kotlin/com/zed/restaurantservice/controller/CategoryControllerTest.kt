package com.zed.restaurantservice.controller

import com.zed.restaurantservice.application.controller.CategoryController
import com.zed.restaurantservice.application.dto.toDomain
import com.zed.restaurantservice.domain.services.CategoryService
import com.zed.restaurantservice.factories.CategoryFactory
import com.zed.restaurantservice.factories.CategoryRequestFactory
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult

class CategoryControllerTest {
    private lateinit var categoryService: CategoryService
    private lateinit var categoryController: CategoryController
    private lateinit var result: BindingResult

    @BeforeEach
    fun beforeEach() {
        categoryService =  mockk(relaxed = true)
        categoryController = CategoryController(categoryService)
        result = mockk(relaxed = true)
    }

    @Test
    fun `given valid category request should return category and http status 201`() {
        val request = CategoryRequestFactory.sample()
        val category = CategoryFactory.sample()

        every { categoryService.create(request.toDomain()) } returns category

        val controller = categoryController.register(request, result)

        verify { categoryService.create(any()) }
        Assertions.assertEquals(HttpStatus.CREATED, controller.statusCode)
    }

    @Test
    fun `given valid request list of categories should return list of categories and http status 200`() {
        val category = CategoryFactory.sample()

        every { categoryService.findAll() } returns arrayListOf(category)

        val controller = categoryController.findAll()

        verify { categoryService.findAll() }
        Assertions.assertEquals(HttpStatus.OK, controller.statusCode)
    }
}