package com.handsome.api.infratructure.postgres.company

import com.handsome.api.domain.Company
import com.handsome.api.domain.CompanyId

interface CompanyRepository {

    fun createCompany(company: Company): CompanyId?
    fun findCompany(companyId: CompanyId): Company?
    fun deleteCompany(companyId: CompanyId)
}
