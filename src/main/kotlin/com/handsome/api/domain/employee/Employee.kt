package com.handsome.api.domain.employee

import com.handsome.api.domain.company.CompanyId
import java.time.OffsetDateTime
import java.util.UUID

data class EmployeeId(val value: UUID) {
    constructor(uuidStr: String) : this(UUID.fromString(uuidStr))

    override fun toString(): String = value.toString()

    companion object {
        fun fromNullableUUID(maybeUUID: UUID?): EmployeeId? {
            return if (maybeUUID == null) {
                null
            } else {
                EmployeeId(maybeUUID)
            }
        }
    }
}

data class Employee(
    val id: EmployeeId,
    val companyId: CompanyId,
    val firstName: String,
    val lastName: String,
    val email: String,
    val position: String,
    val createdAt: OffsetDateTime = OffsetDateTime.now(),
    val deletedAt: OffsetDateTime? = null
)
