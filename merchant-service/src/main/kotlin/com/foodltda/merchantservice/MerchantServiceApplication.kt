package com.foodltda.merchantservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.time.LocalTime

@SpringBootApplication
class MerchantServiceApplication
//        val legalPersonRepository: LegalPersonRepository,
//        val loginPersonRepository: LoginPersonRepository,
//        val foodCategoryRepository: FoodCategoryRepository,
//        val restaurantRepository: RestaurantRepository,
//        val tagRepository: TagRepository,
//        val productsRepository: ProductsRepository,
//) : CommandLineRunner {
//    override fun run(vararg args: String?) {
//        val legalPerson = LegalPerson(
//				id = "1",
//                name = "Kaique",
//                email = "kaique@email.com",
//                cnpj = "91251373000190",
//                address = Address(
//                        street = "Rua hadock lobo",
//                        number = "75",
//                        complement = "ap85",
//                        district = "Jardim Paulista",
//                        city = "Sao Paulo",
//                        state = "SP",
//                        country = "Brasil",
//                        postalCode = "08152-060"
//                ),
//                password = "12345678",
//                telephone = "40028922"
//        )
//
//        val legalPerson2 = LegalPerson(
//				id = "2",
//                name = "Henrique",
//                email = "Henrique@email.com",
//                cnpj = "91251373000180",
//                address = Address(
//                        street = "Rua hadock lobo",
//                        number = "75",
//                        complement = "ap85",
//                        district = "Jardim Paulista",
//                        city = "Sao Paulo",
//                        state = "SP",
//                        country = "Brasil",
//                        postalCode = "08152-060"
//                ),
//                password = "12345678",
//                telephone = "40028924"
//        )
//
//        val legalPerson3 = LegalPerson(
//				id = "3",
//                name = "Gabriel",
//                email = "Gabriel@email.com",
//                cnpj = "91251373000170",
//                address = Address(
//                        street = "Rua hadock lobo",
//                        number = "75",
//                        complement = "ap85",
//                        district = "Jardim Paulista",
//                        city = "Sao Paulo",
//                        state = "SP",
//                        country = "Brasil",
//                        postalCode = "08152-060"
//                ),
//                password = "12345678",
//                telephone = "40028925"
//        )
//
//        legalPersonRepository.save(legalPerson)
//        legalPersonRepository.save(legalPerson2)
//        legalPersonRepository.save(legalPerson3)
//
//        val loginUser = LoginUser(
//                id = "1",
//                email = "kaique@email.com",
//                password = "123456789"
//        )
//        val loginUser2 = LoginUser(
//                id = "2",
//                email = "henrique@email.com",
//                password = "123456789"
//        )
//        val loginUser3 = LoginUser(
//                id = "3",
//                email = "gabriel@email.com",
//                password = "123456789"
//        )
//
//        loginPersonRepository.save(loginUser)
//        loginPersonRepository.save(loginUser2)
//        loginPersonRepository.save(loginUser3)
//
//        val foodCategory1 = FoodCategory(
//                name = "Japonesa"
//        )
//
//        val foodCategory2 = FoodCategory(
//                name = "Churrasco"
//        )
//
//        val foodCategory3 = FoodCategory(
//                name = "Acai"
//        )
//
//        foodCategoryRepository.save(foodCategory1)
//        foodCategoryRepository.save(foodCategory2)
//        foodCategoryRepository.save(foodCategory3)
//
//        val d1 = DeliveryTime(
//                dayOfWeek = DayOfWeek.FRIDAY,
//                openThe = "12:00:00.000000",
//                closeThe = "20:00:00.000000"
//        )
//        val d2 = DeliveryTime(
//                dayOfWeek = DayOfWeek.SUNDAY,
//                openThe = "16:00:00.000000",
//                closeThe = "04:00:00.000000"
//        )
//        val d3 = DeliveryTime(
//                dayOfWeek = DayOfWeek.MONDAY,
//                openThe = "12:00:00.000000",
//                closeThe = "05:00:00.000000"
//        )
//        val d4 = DeliveryTime(
//                dayOfWeek = DayOfWeek.WEDNESDAY,
//                openThe = "12:00:00.000000",
//                closeThe = "20:00:00.000000"
//        )
//
//        val time = mutableListOf<DeliveryTime>()
//        time.add(d1)
//        time.add(d2)
//        time.add(d3)
//        time.add(d4)
//
////		2021-01-24T23:33:52.217235
//
//
//        val restaurant =
//                Restaurant(
//                        name = "Sushi Abc Delivery",
//                        slug = Slugify().slugify("Sushi Abc Delivery"),
//                        legalPersonId = "1",
//                        image = "45454545445",
//                        address = Address(
//                                street = "Rua pinto coelho ",
//                                number = "90",
//                                complement = "casa 3",
//                                district = "Vila Almeida",
//                                city = "Santo Andre",
//                                state = "SP",
//                                country = "Brasil",
//                                postalCode = "08152-060"
//                        ),
//                        telephone = "080056851",
//                        deliveryTime = time,
//                        foodCategory = mutableListOf(foodCategory1),
//                        paymentMethods = mutableListOf(Payment.PAG_SEGURO, Payment.PICPAY)
//                )
//
//
//        val restaurant2 =
//            Restaurant(
//                    name = "Acai da Vila",
//                    slug = Slugify().slugify("Acai da Vila"),
//                    legalPersonId = "2",
//                    image = "45454545446",
//                    address = Address(
//                            street = "Rua pinto coelho ",
//                            number = "90",
//                            complement = "casa 3",
//                            district = "Vila Almeida",
//                            city = "Santo Andre",
//                            state = "SP",
//                            country = "Brasil",
//                            postalCode = "08152-060"
//                    ),
//                    telephone = "080056852",
//                    deliveryTime = time,
//                    foodCategory = mutableListOf(foodCategory1),
//                    paymentMethods = mutableListOf(Payment.PAG_SEGURO, Payment.WALLET)
//            )
//
//
//        val restaurant3 =
//            Restaurant(
//                    name = "Churrasco do pedro",
//                    slug = Slugify().slugify("Churrasco do pedro"),
//                    legalPersonId = "3",
//                    image = "45454545446",
//                    address = Address(
//                            street = "Rua pinto coelho ",
//                            number = "90",
//                            complement = "casa 3",
//                            district = "Vila Almeida",
//                            city = "Santo Andre",
//                            state = "SP",
//                            country = "Brasil",
//                            postalCode = "08152-060"
//                    ),
//                    telephone = "080056853",
//                    deliveryTime = time,
//                    foodCategory = mutableListOf(foodCategory1),
//                    paymentMethods = mutableListOf(Payment.WALLET)
//            )
//
//
//        restaurantRepository.save(restaurant)
//        restaurantRepository.save(restaurant2)
//        restaurantRepository.save(restaurant3)
//    }
//
//}

fun main(args: Array<String>) {
    runApplication<MerchantServiceApplication>(*args)
}
