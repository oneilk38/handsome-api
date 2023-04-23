package com.handsome.api.usecases.employee

import com.handsome.api.domain.EmployeeId
import com.handsome.api.infratructure.postgres.EmployeeRepository
import org.springframework.stereotype.Component

@Component
class EmployeeFinderUseCase(
    private val employeeRepository: EmployeeRepository
) {
    fun find(employeeId: EmployeeId) = employeeRepository.find(
        employeeId
    )
    fun findAll() = employeeRepository.findAll()
}
