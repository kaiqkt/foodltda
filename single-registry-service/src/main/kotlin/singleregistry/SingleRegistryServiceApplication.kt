package singleregistry

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.data.annotation.Id
import singleregistry.domain.entities.individual.Individual
import singleregistry.domain.entities.legal.BusinessType
import singleregistry.domain.entities.legal.Legal
import singleregistry.domain.entities.person.Address
import singleregistry.domain.entities.person.Person
import singleregistry.domain.entities.person.PersonType
import singleregistry.domain.entities.person.Phone
import singleregistry.domain.repositories.IndividualRepository
import singleregistry.domain.repositories.LegalRepository
import singleregistry.domain.repositories.PersonRepository
import singleregistry.domain.services.IndividualService
import singleregistry.domain.services.LegalService
import singleregistry.domain.services.PersonService
import java.time.LocalDateTime

@EnableFeignClients
@SpringBootApplication
class SingleRegistryServiceApplication(
	private val individualService: IndividualService,
	private val legalService: LegalService,
	private val individualRepository: IndividualRepository,
	private val legalRepository: LegalRepository,
	private val personRepository: PersonRepository
): CommandLineRunner {


	override fun run(vararg args: String?) {
		individualRepository.deleteAll()
		legalRepository.deleteAll()
		personRepository.deleteAll()

		val i1 = Individual(
			_id ="1234",
			name = "kaique beserra gomes",
			nickname = "kaique",
			cpf = "09656322886",
			person = Person(
				email = "kaique@email.com",
				address = Address(
					street = "Rua Horacio de carvalho",
					number = "75",
					complement = "ap22",
					district = "Taboao",
					city = "Sao bernardo do campo",
					state = "Sao paulo",
					country = "Brasil",
					postalCode = "09961080"
				),
				phone = Phone(
					countryCode = "+55",
					areaCode = "11",
					number = "996792806"
				),
				type = PersonType.PF,
				createdAt = LocalDateTime.now()
			)
		)

		val l1 = Legal(
			_id ="4567",
			businessName = "nilton bahiano",
			cnpj = "02638825000161",
			businessType = BusinessType.LTDA,
			person = Person(
				email = "bahiano@email.com",
				address = Address(
					street = "Rua Horacio de carvalho",
					number = "75",
					complement = "ap22",
					district = "Taboao",
					city = "Sao bernardo do campo",
					state = "Sao paulo",
					country = "Brasil",
					postalCode = "09961080"
				),
				phone = Phone(
					countryCode = "+55",
					areaCode = "12",
					number = "996792806"
				),
				type = PersonType.PF,
				createdAt = LocalDateTime.now()
			)
		)

		val l2 = Legal(
			_id ="7891",
			businessName = "luca morguinho",
			cnpj = "07159670000102",
			businessType = BusinessType.LTDA,
			person = Person(
				email = "luca@email.com",
				address = Address(
					street = "Rua Horacio de carvalho",
					number = "75",
					complement = "ap22",
					district = "Taboao",
					city = "Sao bernardo do campo",
					state = "Sao paulo",
					country = "Brasil",
					postalCode = "09961080"
				),
				phone = Phone(
					countryCode = "+55",
					areaCode = "13",
					number = "996792806"
				),
				type = PersonType.PJ,
				createdAt = LocalDateTime.now()
			)
		)

		val l3 = Legal(
			_id ="13509",
			businessName = "ZED",
			cnpj = "55880931000113",
			businessType = BusinessType.LTDA,
			person = Person(
				email = "ZED@email.com",
				address = Address(
					street = "Rua Horacio de carvalho",
					number = "75",
					complement = "ap22",
					district = "Taboao",
					city = "Sao bernardo do campo",
					state = "Sao paulo",
					country = "Brasil",
					postalCode = "09961080"
				),
				phone = Phone(
					countryCode = "+55",
					areaCode = "13",
					number = "996792706"
				),
				type = PersonType.PJ,
				createdAt = LocalDateTime.now()
			)
		)

		val l4 = Legal(
			_id ="79504",
			businessName = "TALON",
			cnpj = "52883740000117",
			businessType = BusinessType.LTDA,
			person = Person(
				email = "TALON@email.com",
				address = Address(
					street = "Rua Horacio de carvalho",
					number = "75",
					complement = "ap22",
					district = "Taboao",
					city = "Sao bernardo do campo",
					state = "Sao paulo",
					country = "Brasil",
					postalCode = "09961080"
				),
				phone = Phone(
					countryCode = "+55",
					areaCode = "13",
					number = "976792706"
				),
				type = PersonType.PJ,
				createdAt = LocalDateTime.now()
			)
		)

		individualService.create(i1, "TT#55aaa")
		legalService.create(l4, "TT#55aaa")
		legalService.create(l2, "TT#55aaa")
		legalService.create(l3, "TT#55aaa")
		legalService.create(l1, "TT#55aaa")

	}

}

fun main(args: Array<String>) {
	runApplication<SingleRegistryServiceApplication>(*args)
}
