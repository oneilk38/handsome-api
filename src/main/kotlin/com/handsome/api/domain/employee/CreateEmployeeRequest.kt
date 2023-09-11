package com.handsome.api.domain.employee

import com.handsome.api.domain.company.CompanyId
import java.util.*

data class CreateEmployeeRequest(
    val companyId: CompanyId,
    val firstName: String,
    val lastName: String,
    val email: String,
    val position: String
) {
    fun toCreateEmployeeRequest() = Employee(
        id = EmployeeId(UUID.randomUUID()),
        companyId = companyId,
        firstName = firstName,
        lastName = lastName,
        email = email,
        position = position
    )
}
