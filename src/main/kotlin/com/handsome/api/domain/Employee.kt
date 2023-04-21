package com.handsome.api.domain

data class EmployeeId(val value: Long){
    companion object {
        fun fromInt(value: Int) = EmployeeId(value.toLong())
    }
}

data class Employee(
    val id: EmployeeId,
    val firstName: String,
    val lastName: String,
    val email: String,
    val position: String
)

data class EmployeeCreationRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val position: String
)