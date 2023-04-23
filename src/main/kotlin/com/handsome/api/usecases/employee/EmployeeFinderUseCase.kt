package com.handsome.api.usecases.employee

import com.handsome.api.domain.CompanyId
import com.handsome.api.domain.EmployeeId
import com.handsome.api.infratructure.postgres.employee.EmployeeRepository
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
