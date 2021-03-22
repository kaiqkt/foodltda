package authorizationservice.domain.repositories

import authorizationservice.domain.entities.Phone
import authorizationservice.domain.entities.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository: MongoRepository<User, String> {
    fun existsByPersonId(personId: String): Boolean
    fun existsByEmail(email: String): Boolean
    fun existsByPhone(phone: Phone?): Boolean
    fun findByEmail(email: String?): User?
    fun findByPersonId(email: String?): User?
}