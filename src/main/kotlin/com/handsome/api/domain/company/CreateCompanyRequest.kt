package com.handsome.api.domain.company

import java.util.UUID

data class CreateCompanyRequest(
    val companyName: String,
    val countryCode: CountryCode
) {
    fun toCompany() = Company(
        id = CompanyId(UUID.randomUUID()),
        companyName = companyName,
        countryCode = countryCode
    )
}
