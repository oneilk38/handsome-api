package com.handsome.api.domain.employee

import com.handsome.api.domain.company.CompanyId
import java.time.LocalDateTime
import java.util.UUID

data class EmployeeId(val value: UUID) {
    override fun toString(): String = value.toString()
}

data class Employee(
    val id: EmployeeId,
    val companyId: CompanyId,
    val firstName: String,
    val lastName: String,
    val email: String,
    val position: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val deletedAt: LocalDateTime? = null
)
