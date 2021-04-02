package singleregistry.domain.repositories

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import singleregistry.domain.entities.person.Person
import singleregistry.domain.entities.person.Phone

@Repository
interface PersonRepository: MongoRepository<Person, String> {
    fun findByEmail(email: String?): Person?
    fun existsByEmail(email: String?): Boolean
    fun existsByPhone(phone: Phone?): Boolean
    fun existsByPersonId(personId: String): Boolean
}