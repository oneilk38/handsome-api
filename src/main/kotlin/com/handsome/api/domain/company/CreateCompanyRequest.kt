package com.handsome.api.domain.company

import java.util.UUID

data class CreateCompanyRequest(
    val id: UUID,
    val companyName: String,
    val countryCode: CountryCode
) {
    fun toCompany() = Company(
        id = CompanyId(id),
        companyName = companyName,
        countryCode = countryCode
    )
}
