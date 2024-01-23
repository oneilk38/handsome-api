package com.handsome.api.infratructure.postgres.company

import com.handsome.api.domain.company.Company
import com.handsome.api.domain.company.CompanyId
import com.handsome.api.domain.company.CompanyRepository
import com.handsome.api.domain.company.CountryCode
import com.handsome.api.http.response.CompanyAlreadyExistsException
import org.jooq.DSLContext
import org.jooq.generated.tables.Companies.COMPANIES
import org.jooq.generated.tables.records.CompaniesRecord
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

@Repository
class PostgresCompanyRepository(
    private val dslContext: DSLContext
) : CompanyRepository {
    private val logger: Logger = LoggerFactory.getLogger(PostgresCompanyRepository::class.java)

    override fun createCompany(company: Company): CompanyId? {
        try {
            return dslContext.insertInto(COMPANIES)
                .set(COMPANIES.ID, company.id.value)
                .set(COMPANIES.COMPANY_NAME, company.companyName)
                .set(COMPANIES.COUNTRY_CODE, company.countryCode.toString())
                .set(COMPANIES.CREATED_AT, company.createdAt)
                .returning(COMPANIES.ID)
                .fetchOne()
                .toCompanyId()
        } catch (ex: Exception) {
            if (ex.message?.contains("duplicate key value violates unique constraint", true) == true) {
                throw CompanyAlreadyExistsException(ex.message)
            }
            throw ex
        }
    }

    override fun findCompany(companyId: CompanyId): Company? {
        return dslContext.selectFrom(COMPANIES)
            .where(COMPANIES.ID.eq(companyId.value))
            .fetchOne()?.toCompany()
    }

    override fun findCompanies(): List<Company> {
        return dslContext.selectFrom(COMPANIES)
            .fetch().map { it.toCompany() }
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
