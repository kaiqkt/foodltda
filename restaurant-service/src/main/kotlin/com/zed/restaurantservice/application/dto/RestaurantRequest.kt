package com.zed.restaurantservice.application.dto

import com.zed.restaurantservice.domain.entities.filter.Payment
import com.zed.restaurantservice.domain.entities.restaurant.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class RestaurantRequest(
    @get:NotEmpty(message = "Name cannot be empty.")
    val name: String = "",
    @get:NotEmpty(message = "Image cannot be empty.")
    val image: String = "",
    @get:NotEmpty(message = "Delivery time cannot be null.")
    val delivery: MutableList<DeliveryTime> =  mutableListOf(),
    @get:NotEmpty(message = "Restaurant Filter cannot be empty.")
    val restaurantFilter: String = "",
    @get:NotEmpty(message = "Payment methods cannot be null")
    val payment: MutableList<Payment> = mutableListOf(),
    @get:NotEmpty(message = "Street cannot be empty.")
    val street: String = "",
    @get:NotEmpty(message = "Number cannot be empty.")
    val number: String = "",
    val complement: String = "",
    @get:NotEmpty(message = "District cannot be empty.")
    val district: String = "",
    @get:NotEmpty(message = "City cannot be empty.")
    val city: String = "",
    @get:NotEmpty(message = "State cannot be empty.")
    val state: String = "",
    @get:NotEmpty(message = "Country cannot be empty.")
    val country: String = "",
    @get:NotEmpty(message = "PostalCode cannot be empty.")
    @get:Pattern(regexp = "[0-9]{7}", message = "Postal code invalid")
    val postalCode: String = "",
    @get:NotEmpty(message = "Country Code cannot be empty.")
    @get:Pattern(regexp = "\\+?[0-9]{2}", message = "Country code invalid")
    val countryCode: String = "",
    @get:NotEmpty(message = "Area Code cannot be empty.")
    @get:Pattern(regexp = "[0-9]{2}", message = "Area code invalid")
    val areaCode: String = "",
    @get:NotEmpty(message = "Phone Number cannot be empty.")
    @get:Pattern(regexp = "[0-9]{9}", message = "Number invalid")
    val phoneNumber: String = "",
)

fun RestaurantRequest.toDomain() = Restaurant(
    name = this.name,
    image = this.image,
    address = this.toAddress(),
    phone =  this.toPhone(),
    openingHours = delivery,
    category = this.restaurantFilter,
    payments = this.payment
)

fun RestaurantRequest.toAddress() = Address(
    street = this.street,
    number = this.number,
    complement = this.complement,
    district = this.district,
    city = this.city,
    state = this.state,
    country = this.country,
    postalCode = this.postalCode
)

fun RestaurantRequest.toPhone() = Phone(
    countryCode = this.countryCode,
    areaCode = this.areaCode,
    number = this.phoneNumber
)