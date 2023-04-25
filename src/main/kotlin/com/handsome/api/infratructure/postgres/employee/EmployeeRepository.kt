package com.handsome.api.infratructure.postgres.employee

import com.handsome.api.domain.CompanyId
import com.handsome.api.domain.Employee
import com.handsome.api.domain.EmployeeId

interface EmployeeRepository {
    fun create(employee: Employee): EmployeeId?
    fun find(employeeId: EmployeeId, companyId: CompanyId): Employee?
    fun findAllByCompany(companyId: CompanyId): List<Employee>
    fun deleteByCompany(companyId: CompanyId)
}
