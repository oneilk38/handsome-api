package com.handsome.api.domain.company

interface CompanyRepository {

    fun createCompany(company: Company): CompanyId?
    fun findCompany(companyId: CompanyId): Company?
    fun deleteCompany(companyId: CompanyId)
}
