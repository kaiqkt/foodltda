package com.foodltda.merchantservice.domain.entities

import com.foodltda.merchantservice.application.dto.Tag
import com.foodltda.merchantservice.application.dto.request.ProductDTO
import com.github.slugify.Slugify
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

@Document
data class Products (
        @Id
        val id: String,
        val name: String,
        val image: String?,
        val price: BigDecimal,
        val description: String?,
        val tag: Tag
) {
    companion object {
        fun fromDocument(product: ProductDTO) = Products (
                id = Slugify().slugify(product.name),
                name = product.name,
                image = product.image,
                price = product.price,
                description = product.description,
                tag = product.tag
        )
    }
}