package com.handsome.api.usecases.company

import com.handsome.api.domain.CompanyId
import com.handsome.api.infratructure.postgres.company.CompanyRepository
import com.handsome.api.infratructure.postgres.employee.EmployeeRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class CompanyDeletorUseCase(
    private val companyRepository: CompanyRepository,
    private val employeeRepository: EmployeeRepository
) {
    fun deleteCompany(companyId: CompanyId) {
        companyRepository.deleteCompany(companyId)
        employeeRepository.deleteByCompany(companyId)
    }
}
