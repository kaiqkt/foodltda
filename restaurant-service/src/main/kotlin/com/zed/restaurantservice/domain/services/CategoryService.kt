package com.zed.restaurantservice.domain.services

import com.zed.restaurantservice.domain.entities.filter.Category
import com.zed.restaurantservice.domain.exceptions.DataValidationException
import com.zed.restaurantservice.domain.repositories.CategoryRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun create(category: Category): Category {
        validateDate(category)

        return categoryRepository.save(category).also { log.info("Category[${it._id}] created ") }
    }

    fun findAll(): List<Category> {
        val categories = categoryRepository.findAll()
        return categories.sortedBy { it.name }
    }


    fun validateDate(category: Category) {
        val error = mutableListOf<String>()

        category.name.let {
            if (categoryRepository.existsByName(it)) {
                error.add("Category name: $it already use")
            }
        }

        if (error.isNotEmpty()) {
            throw DataValidationException(error)
        }
    }
}