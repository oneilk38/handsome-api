package com.handsome.api.usecases.project

import com.handsome.api.domain.company.CompanyId
import com.handsome.api.domain.employee.EmployeeId
import com.handsome.api.domain.project.ProjectId
import com.handsome.api.domain.project.ProjectRepository
import org.springframework.stereotype.Component

@Component
class ProjectFinderUseCase(
    private val projectRepository: ProjectRepository
) {
    fun findProject(companyId: CompanyId, projectId: ProjectId) = projectRepository.findProject(
        companyId = companyId,
        projectId = projectId
    )

    fun findProjects(companyId: CompanyId, projectIds: List<ProjectId> = emptyList()) = projectRepository.findProjects(
        companyId = companyId,
        projectIds = projectIds
    )

    fun findProjectAssignments(companyId: CompanyId, projectId: ProjectId) = projectRepository.findProjectAssignees(
        companyId = companyId,
        projectId = projectId
    )

    fun findEmployeeProjects(companyId: CompanyId, employeeId: EmployeeId) = projectRepository.findEmployeeProjects(
        companyId = companyId,
        employeeId = employeeId
    )
}
