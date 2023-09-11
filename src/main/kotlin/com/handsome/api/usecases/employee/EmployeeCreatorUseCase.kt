package com.handsome.api.usecases.employee

import com.handsome.api.domain.employee.Employee
import com.handsome.api.domain.employee.EmployeeId
import com.handsome.api.domain.employee.EmployeeRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class EmployeeCreatorUseCase(
    private val employeeRepository: EmployeeRepository
) {
    fun create(employee: Employee): EmployeeId? =
        employeeRepository.create(employee)
}
