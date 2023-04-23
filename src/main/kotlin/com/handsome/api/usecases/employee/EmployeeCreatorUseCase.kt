package com.handsome.api.usecases.employee

import com.handsome.api.domain.EmployeeCreationRequest
import com.handsome.api.domain.EmployeeId
import com.handsome.api.infratructure.postgres.EmployeeRepository
import org.springframework.stereotype.Component

@Component
class EmployeeCreatorUseCase(
    private val employeeRepository: EmployeeRepository
) {
    fun create(employee: EmployeeCreationRequest): EmployeeId? =
        employeeRepository.create(employee)
}
