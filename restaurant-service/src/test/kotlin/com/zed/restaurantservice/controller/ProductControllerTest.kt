package com.zed.restaurantservice.controller

import com.zed.restaurantservice.application.controller.ProductController
import com.zed.restaurantservice.application.dto.toDomain
import com.zed.restaurantservice.domain.services.ProductCategoryService
import com.zed.restaurantservice.domain.services.ProductService
import com.zed.restaurantservice.factories.*
import com.zed.restaurantservice.resources.security.JWTUtil
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import java.util.*

class ProductControllerTest {
    private lateinit var productService: ProductService
    private lateinit var productCategoryService: ProductCategoryService
    private lateinit var productController: ProductController
    private lateinit var result: BindingResult

    @BeforeEach
    fun beforeEach() {
        productService = mockk(relaxed = true)
        productCategoryService = mockk(relaxed = true)
        productController = ProductController(productService, productCategoryService)
        result = mockk(relaxed = true)
    }

    @Test
    fun `given valid product request should return product and http status 201`() {
        val request = ProductRequestFactory.sample()
        val token = UUID.randomUUID().toString()
        val product = ProductFactory.sample()

        every { productService.create(request.toDomain(), token) } returns product

        val controller = productController.create(request, token, result)

        verify { productService.create(any(), any()) }
        Assertions.assertEquals(HttpStatus.CREATED, controller.statusCode)
    }

    @Test
    fun `given valid product category request should return product category and http status 201`() {
        val request = ProductCategoryRequestFactory.sample()
        val token = UUID.randomUUID().toString()
        val productCategory = ProductCategoryFactory.sample()

        every { productCategoryService.create(request.toDomain(), token) } returns productCategory

        val controller = productController.createCategory(request, token, result)

        verify { productCategoryService.create(any(), any()) }
        Assertions.assertEquals(HttpStatus.CREATED, controller.statusCode)
    }

    @Test
    fun `given valid slug and restaurantId should return product and http status 201`() {
        val slug = "slug-sample"
        val restaurantId = "1232421"
        val product = ProductFactory.sample()

        every { productService.findBySlugAndRestaurantId(slug, restaurantId) } returns product

        val controller = productController.findBySlugAndRestaurantID(slug, restaurantId)

        verify { productService.findBySlugAndRestaurantId(any(), any()) }
        Assertions.assertEquals(HttpStatus.OK, controller.statusCode)
    }

    @Test
    fun `given valid category and restaurantId should return products and http status 201`() {
        val category = "category-sample"
        val restaurantId = "1232421"
        val product = ProductFactory.sample()

        every { productService.findAllByCategory(category, restaurantId) } returns arrayListOf(product)

        val controller = productController.findAllByCategory(category, restaurantId)

        verify { productService.findAllByCategory(any(), any()) }
        Assertions.assertEquals(HttpStatus.OK, controller.statusCode)
    }

    @Test
    fun `given valid restaurantId should return all categories and http status 201`() {
        val restaurantId = "1232421"
        val productCategory = ProductCategoryFactory.sample()

        every { productCategoryService.findAllByRestaurantId(restaurantId) } returns arrayListOf(productCategory)

        val controller = productController.findAllCategoryByRestaurantId(restaurantId)

        verify { productCategoryService.findAllByRestaurantId(any()) }
        Assertions.assertEquals(HttpStatus.OK, controller.statusCode)
    }

    @Test
    fun `given valid restaurantId should return all products and http status 201`() {
        val restaurantId = "1232421"
        val product = ProductFactory.sample()

        every { productService.findAll(restaurantId) } returns arrayListOf(product)

        val controller = productController.findAll(restaurantId)

        verify { productService.findAll(any()) }
        Assertions.assertEquals(HttpStatus.OK, controller.statusCode)
    }
}
