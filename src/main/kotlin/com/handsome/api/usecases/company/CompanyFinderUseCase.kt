package com.handsome.api.usecases.company

import com.handsome.api.domain.CompanyId
import com.handsome.api.infratructure.postgres.company.CompanyRepository
import org.springframework.stereotype.Component

@Component
class CompanyFinderUseCase(
    private val companyRepository: CompanyRepository
) {
    fun findCompany(companyId: CompanyId) = companyRepository.findCompany(companyId)
}
