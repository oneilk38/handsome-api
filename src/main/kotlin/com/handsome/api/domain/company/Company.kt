package com.handsome.api.domain.company

import java.time.OffsetDateTime
import java.util.UUID

data class CompanyId(val value: UUID) {
    constructor(uuidStr: String) : this(UUID.fromString(uuidStr))

    override fun toString(): String = value.toString()
}

data class CountryCode(val value: String) {
    init {
        require(value.length == 2 && value.all { it.isLetter() })
    }

    override fun toString(): String = value
}

data class Company(
    val id: CompanyId,
    val companyName: String,
    val countryCode: CountryCode,
    val createdAt: OffsetDateTime = OffsetDateTime.now(),
    val deletedAt: OffsetDateTime? = null
)
