package singleregistry.domain.exceptions

class DataValidationException(
    private val error: List<String>
) : DomainException() {
    override fun details() = error
}