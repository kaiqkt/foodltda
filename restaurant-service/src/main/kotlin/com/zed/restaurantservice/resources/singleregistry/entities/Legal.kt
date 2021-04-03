package com.zed.restaurantservice.resources.singleregistry.entities

data class Legal(
    val _id: String? = null,
    val businessName: String?,
    val cnpj: String?,
    val businessType: BusinessType?
)
