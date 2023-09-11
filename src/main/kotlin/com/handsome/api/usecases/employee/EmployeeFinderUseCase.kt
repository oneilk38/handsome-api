package com.handsome.api.usecases.employee

import com.handsome.api.domain.company.CompanyId
import com.handsome.api.domain.employee.EmployeeId
import com.handsome.api.domain.employee.EmployeeRepository
import org.springframework.stereotype.Component

@Component
class EmployeeFinderUseCase(
    private val employeeRepository: EmployeeRepository
) {
    fun find(employeeId: EmployeeId, companyId: CompanyId) = employeeRepository.find(
        employeeId = employeeId,
        companyId = companyId
    )

    fun findAll(companyId: CompanyId) = employeeRepository.findAllByCompany(companyId)
}
