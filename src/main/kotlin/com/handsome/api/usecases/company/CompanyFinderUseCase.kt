package com.handsome.api.usecases.company

import com.handsome.api.domain.company.CompanyId
import com.handsome.api.domain.company.CompanyRepository
import org.springframework.stereotype.Component

@Component
class CompanyFinderUseCase(
    private val companyRepository: CompanyRepository
) {
    fun findCompany(companyId: CompanyId) = companyRepository.findCompany(companyId)
    fun findCompanies() = companyRepository.findCompanies()
}
