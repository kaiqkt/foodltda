package singleregistry.domain.repositories

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import singleregistry.domain.entities.Legal
import singleregistry.domain.entities.person.Person

@Repository
interface PersonRepository: MongoRepository<Person, String> {
}