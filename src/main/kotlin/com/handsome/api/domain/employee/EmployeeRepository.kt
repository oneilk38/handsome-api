package com.handsome.api.domain.employee

import com.handsome.api.domain.company.CompanyId

interface EmployeeRepository {
    fun create(employee: Employee): EmployeeId?
    fun find(employeeId: EmployeeId, companyId: CompanyId): Employee?
    fun findAllByCompany(companyId: CompanyId): List<Employee>
    fun deleteByCompany(companyId: CompanyId)
}
