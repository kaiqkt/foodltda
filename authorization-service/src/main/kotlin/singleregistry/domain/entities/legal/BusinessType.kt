package singleregistry.domain.entities.legal

import com.fasterxml.jackson.annotation.JsonProperty

enum class BusinessType(val value: String) {

    @JsonProperty("S/A")
    SA("S/A"),
    LTDA("LTDA")
}