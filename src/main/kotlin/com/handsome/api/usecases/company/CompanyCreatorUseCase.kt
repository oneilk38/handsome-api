package com.handsome.api.usecases.company

import com.handsome.api.domain.Company
import com.handsome.api.domain.CompanyId
import com.handsome.api.infratructure.postgres.company.CompanyRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class CompanyCreatorUseCase(
    private val companyRepository: CompanyRepository
) {
    fun createCompany(company: Company): CompanyId? =
        companyRepository.createCompany(company)
}
