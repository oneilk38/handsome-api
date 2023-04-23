package com.handsome.api.http

import com.handsome.api.domain.Employee
import com.handsome.api.domain.EmployeeCreationRequest
import com.handsome.api.domain.EmployeeId
import com.handsome.api.usecases.employee.EmployeeCreatorUseCase
import com.handsome.api.usecases.employee.EmployeeFinderUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Suppress("unused")
@RestController
class EmployeeController(
    private val employeeFinderUseCase: EmployeeFinderUseCase,
    private val employeeCreatorUseCase: EmployeeCreatorUseCase
) {

    @GetMapping("/employees/{employeeId}")
    private fun getEmployee(@PathVariable employeeId: Long): Employee? {
        return employeeFinderUseCase.find(EmployeeId(employeeId))
    }

    @GetMapping("/employees")
    private fun getEmployees(): List<Employee> {
        return employeeFinderUseCase.findAll()
    }

    @PostMapping("/employees")
    private fun createEmployee(@RequestBody employeeCreatorRequest: EmployeeCreationRequest): EmployeeId? {
        return employeeCreatorUseCase.create(employeeCreatorRequest)
    }
}
