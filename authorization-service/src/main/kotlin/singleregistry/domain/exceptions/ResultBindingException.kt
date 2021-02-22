package singleregistry.domain.exceptions

class ResultBindingException(
    private val error: List<String>
) : DomainException() {
    override fun details() = error
}