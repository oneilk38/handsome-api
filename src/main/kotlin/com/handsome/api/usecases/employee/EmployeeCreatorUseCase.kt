package com.handsome.api.usecases.employee

import com.handsome.api.domain.Employee
import com.handsome.api.domain.EmployeeId
import com.handsome.api.infratructure.postgres.employee.EmployeeRepository
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
