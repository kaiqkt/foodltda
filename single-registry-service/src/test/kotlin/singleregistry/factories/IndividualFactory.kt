package singleregistry.factories

import singleregistry.domain.entities.individual.Individual
import singleregistry.domain.entities.person.PersonType

object IndividualFactory {
    fun sample() = Individual(
        name = "Kaique Gomes",
        nickname = "kaique",
        cpf = "221.670.888-76",
        person = PersonFactory.sample(personType = PersonType.PJ)
    )
}