package com.handsome.api.infratructure.postgres

import com.handsome.api.domain.Employee
import com.handsome.api.domain.EmployeeCreationRequest
import com.handsome.api.domain.EmployeeId


interface EmployeeRepository {
    fun create(employee: EmployeeCreationRequest)
    fun find(employeeId: EmployeeId): Employee?
    fun findAll(): List<Employee>
}