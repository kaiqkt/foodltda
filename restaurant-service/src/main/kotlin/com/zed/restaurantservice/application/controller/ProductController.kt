package com.zed.restaurantservice.application.controller

import com.zed.restaurantservice.application.dto.CategoryRequest
import com.zed.restaurantservice.application.dto.ProductCategoryRequest
import com.zed.restaurantservice.application.dto.ProductRequest
import com.zed.restaurantservice.application.dto.toDomain
import com.zed.restaurantservice.application.validation.JsonValidator
import com.zed.restaurantservice.domain.entities.restaurant.Product
import com.zed.restaurantservice.domain.entities.restaurant.ProductCategory
import com.zed.restaurantservice.domain.services.ProductCategoryService
import com.zed.restaurantservice.domain.services.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product")
class ProductController(
    private val productService: ProductService,
    private val productCategoryService: ProductCategoryService
) {

    @PostMapping
    fun create(
        @RequestBody request: ProductRequest,
        @RequestHeader("Authorization") auth_token: String,
        result: BindingResult
    ): ResponseEntity<Product> {
        JsonValidator.validate(result)

        return ResponseEntity<Product>(
            productService.create(request.toDomain(), auth_token),
            HttpStatus.CREATED
        )
    }

    @PostMapping("/category")
    fun createCategory(
        @RequestBody request: ProductCategoryRequest,
        @RequestHeader("Authorization") auth_token: String,
        result: BindingResult
    ): ResponseEntity<ProductCategory> {
        JsonValidator.validate(result)

        return ResponseEntity<ProductCategory>(
            productCategoryService.create(request.toDomain(), auth_token),
            HttpStatus.CREATED
        )
    }

    @GetMapping("/slug/{slug}/restaurant/{restaurantId}")
    fun findBySlug(@PathVariable slug: String, @PathVariable restaurantId: String): ResponseEntity<Product> {

        return ResponseEntity<Product>(
            productService.findBySlugAndRestaurantId(slug, restaurantId),
            HttpStatus.OK
        )
    }

    @GetMapping("/category/{category}/restaurant/{restaurantId}")
    fun findByCategory(
        @PathVariable category: String,
        @PathVariable restaurantId: String
    ): ResponseEntity<List<Product>> {

        return ResponseEntity<List<Product>>(
            productService.findAllByCategory(category, restaurantId),
            HttpStatus.OK
        )
    }

    @GetMapping("/category/{restaurantId}/categories")
    fun findAllCategoryByRestaurant(
        @PathVariable restaurantId: String
    ): ResponseEntity<List<ProductCategory>> {

        return ResponseEntity<List<ProductCategory>>(
            productCategoryService.findAllByRestaurantId(restaurantId),
            HttpStatus.OK
        )
    }

    @GetMapping("/{id}/products")
    fun findAll(@PathVariable id: String): ResponseEntity<List<Product>> {

        return ResponseEntity<List<Product>>(
            productService.findAll(id),
            HttpStatus.OK
        )
    }
}