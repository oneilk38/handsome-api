package com.handsome.api.domain

import java.time.LocalDateTime
import java.util.UUID

data class EmployeeId(val value: UUID) {
    constructor(uuidStr: String) : this(UUID.fromString(uuidStr))
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
