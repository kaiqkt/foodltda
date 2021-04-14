package com.foodltda.merchantservice.domain.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import com.foodltda.merchantservice.application.dto.request.ProductDTO
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

@Document
data class Products (
        @Id
        val id: String? = null,
        var slug: String?,
        val name: String?,
        val image: String?,
        val price: BigDecimal?,
        val description: String?,
        val tag: Tag?,
        @JsonIgnore
        val restaurantId: String
) {
    companion object{
        fun fromDocument(product: ProductDTO, slug: String?, id: String, tag: Tag?) = Products (
                name = product.name,
                slug = slug,
                image = product.image,
                price = product.price,
                description = product.description,
                tag = tag,
                restaurantId = id
        )
    }
}