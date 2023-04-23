package com.handsome.api.http

import com.handsome.api.domain.Company
import com.handsome.api.domain.CompanyId
import com.handsome.api.usecases.company.CompanyCreatorUseCase
import com.handsome.api.usecases.company.CompanyDeletorUseCase
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
    private val companyDeletorUseCase: CompanyDeletorUseCase,
    private val companyFinderUseCase: CompanyFinderUseCase
) {
    @GetMapping("/company/{id}")
    private fun getCompany(@PathVariable id: UUID) =
        companyFinderUseCase.findCompany(CompanyId(id))

    @PostMapping("/company")
    fun createCompany(@RequestBody company: Company): CompanyId? =
        companyCreatorUseCase.createCompany(company)

    @DeleteMapping("/company/{id}")
    fun deleteCompany(@PathVariable id: UUID) =
        companyDeletorUseCase.deleteCompany(CompanyId(id))
}
