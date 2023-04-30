package com.handsome.api.usecases.project

import com.handsome.api.domain.company.CompanyId
import com.handsome.api.domain.employee.EmployeeId
import com.handsome.api.domain.project.ProjectAssignment
import com.handsome.api.domain.project.ProjectId
import com.handsome.api.domain.project.ProjectRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class ProjectAssignorUseCase(
    private val projectRepository: ProjectRepository
) {
    fun assign(projectAssignment: ProjectAssignment) = projectRepository.assignToProject(projectAssignment)
    fun remove(
        companyId: CompanyId,
        projectId: ProjectId,
        employeeId: EmployeeId
    ) = projectRepository.removeFromProject(
        companyId = companyId,
        projectId = projectId,
        employeeId = employeeId,
        deletedAt = LocalDateTime.now()
    )
}
