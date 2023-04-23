package com.handsome.api.infratructure.postgres.company

import com.handsome.api.domain.Company
import com.handsome.api.domain.CompanyId
import com.handsome.api.domain.CountryCode
import org.jooq.DSLContext
import org.jooq.generated.tables.Companies.COMPANIES
import org.jooq.generated.tables.records.CompaniesRecord
import org.springframework.stereotype.Repository

@Repository
class PostgresCompanyRepository(
    private val dslContext: DSLContext
) : CompanyRepository {
    override fun createCompany(company: Company): CompanyId? {
        return dslContext.insertInto(COMPANIES)
            .set(COMPANIES.ID, company.id.value)
            .set(COMPANIES.COMPANY_NAME, company.companyName)
            .set(COMPANIES.COUNTRY_CODE, company.countryCode.toString())
            .set(COMPANIES.CREATED_AT, company.createdAt)
            .returning(COMPANIES.ID)
            .fetchOne()
            .toCompanyId()
    }

    override fun findCompany(companyId: CompanyId): Company? {
        return dslContext.selectFrom(COMPANIES)
            .where(COMPANIES.ID.eq(companyId.value))
            .fetchOne()?.toCompany()
    }

    override fun deleteCompany(companyId: CompanyId) {
        dslContext.deleteFrom(COMPANIES).where(COMPANIES.ID.eq(companyId.value)).execute()
    }

    private fun CompaniesRecord?.toCompanyId(): CompanyId? {
        if (this == null) {
            return null
        }

        return CompanyId(this.get(COMPANIES.ID))
    }

    private fun CompaniesRecord.toCompany() = Company(
        id = CompanyId(this[COMPANIES.ID]),
        companyName = this[COMPANIES.COMPANY_NAME],
        countryCode = CountryCode(this[COMPANIES.COUNTRY_CODE]),
        createdAt = this[COMPANIES.CREATED_AT],
        deletedAt = this[COMPANIES.DELETED_AT]
    )
}
