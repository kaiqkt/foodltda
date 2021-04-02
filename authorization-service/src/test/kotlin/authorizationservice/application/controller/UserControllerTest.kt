package authorizationservice.application.controller

import authorizationservice.application.dto.toDomain
import authorizationservice.domain.services.UserService
import authorizationservice.factories.UserFactory
import authorizationservice.factories.UserRequestFactory
import io.mockk.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult

class UserControllerTest {
    private lateinit var userService: UserService
    private lateinit var userController: UserController
    private lateinit var result: BindingResult

    @BeforeEach
    fun beforeEach() {
        userService =  mockk(relaxed = true)
        userController = UserController(userService)
        result = mockk(relaxed = true)
    }

    @Test
    fun `given valid user request should return user and http status 201`() {
        val request = UserRequestFactory.sample()
        val user = UserFactory.sample()

        every { userService.create(request.toDomain()) } returns user

        val controller = userController.register(request, result)

        verify { userService.create(any()) }
        Assertions.assertEquals(HttpStatus.CREATED, controller.statusCode)
    }

    @Test
    fun `given valid delete request should return http status 200`() {

        every { userService.deleteSession() } just runs

        val controller = userController.logout()

        verify { userService.deleteSession() }
        Assertions.assertEquals(HttpStatus.OK, controller.statusCode)
    }
}