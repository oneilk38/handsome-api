package com.handsome.api.http

import com.handsome.api.domain.company.CompanyId
import com.handsome.api.domain.company.CreateCompanyRequest
import com.handsome.api.usecases.company.CompanyCreatorUseCase
import com.handsome.api.usecases.company.CompanyDeleterUseCase
import com.handsome.api.usecases.company.CompanyFinderUseCase
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@Suppress("unused")
class CompanyController(
    private val companyCreatorUseCase: CompanyCreatorUseCase,
    private val companyDeleterUseCase: CompanyDeleterUseCase,
    private val companyFinderUseCase: CompanyFinderUseCase
) {
    @GetMapping("/admin/company/{id}")
    private fun getCompany(@PathVariable id: UUID) =
        companyFinderUseCase.findCompany(CompanyId(id))

    @GetMapping("/admin/companies")
    private fun getCompanies() =
        companyFinderUseCase.findCompanies()

    @PostMapping("/admin/company")
    fun createCompany(@RequestBody request: CreateCompanyRequest): CompanyId? =
        companyCreatorUseCase.createCompany(request.toCompany())

    @DeleteMapping("/admin/company/{id}")
    fun deleteCompany(@PathVariable id: UUID) =
        companyDeleterUseCase.deleteCompany(CompanyId(id))
}
