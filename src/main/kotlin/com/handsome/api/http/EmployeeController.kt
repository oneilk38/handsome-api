package com.handsome.api.http

import com.handsome.api.domain.company.CompanyId
import com.handsome.api.domain.employee.CreateEmployeeRequest
import com.handsome.api.domain.employee.Employee
import com.handsome.api.domain.employee.EmployeeId
import com.handsome.api.usecases.employee.EmployeeCreatorUseCase
import com.handsome.api.usecases.employee.EmployeeFinderUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@Suppress("unused")
@RestController
class EmployeeController(
    private val employeeFinderUseCase: EmployeeFinderUseCase,
    private val employeeCreatorUseCase: EmployeeCreatorUseCase
) {

    @GetMapping("/company/{companyId}/employees/{employeeId}")
    private fun getEmployee(@PathVariable companyId: UUID, @PathVariable employeeId: UUID): Employee? {
        return employeeFinderUseCase.find(
            employeeId = EmployeeId(employeeId),
            companyId = CompanyId(companyId)
        )
    }

    @GetMapping("/company/{companyId}/employees")
    private fun getEmployees(@PathVariable companyId: UUID): List<Employee> {
        return employeeFinderUseCase.findAll(CompanyId(companyId))
    }

    @PostMapping("/employees")
    private fun createEmployee(@RequestBody request: CreateEmployeeRequest): EmployeeId? {
        return employeeCreatorUseCase.create(request.toCreateEmployeeRequest())
    }
}
