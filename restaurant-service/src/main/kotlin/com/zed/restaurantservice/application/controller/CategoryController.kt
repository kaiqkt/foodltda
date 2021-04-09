package com.zed.restaurantservice.application.controller

import com.zed.restaurantservice.application.dto.CategoryRequest
import com.zed.restaurantservice.application.dto.toDomain
import com.zed.restaurantservice.application.validation.JsonValidator
import com.zed.restaurantservice.domain.entities.filter.Category
import com.zed.restaurantservice.domain.services.CategoryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/category")
class CategoryController(private val categoryService: CategoryService) {

    @PostMapping
    fun register(@Valid @RequestBody categoryRequest: CategoryRequest, result: BindingResult): ResponseEntity<Category> {
        JsonValidator.validate(result)

        return ResponseEntity<Category>(
            categoryService.create(categoryRequest.toDomain()),
            HttpStatus.CREATED
        )
    }

    @GetMapping("/categories")
    fun findAll(): ResponseEntity<List<Category>> {

        return ResponseEntity<List<Category>>(
            categoryService.findAll(),
            HttpStatus.CREATED
        )
    }
}