package com.handsome.api.usecases.company

import com.handsome.api.domain.company.CompanyId
import com.handsome.api.domain.company.CompanyRepository
import com.handsome.api.domain.employee.EmployeeRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class CompanyDeleterUseCase(
    private val companyRepository: CompanyRepository,
    private val employeeRepository: EmployeeRepository
) {
    fun deleteCompany(companyId: CompanyId) {
        companyRepository.deleteCompany(companyId)
        employeeRepository.deleteByCompany(companyId)
    }
}
