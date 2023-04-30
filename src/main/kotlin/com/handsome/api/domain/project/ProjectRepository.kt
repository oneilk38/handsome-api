package com.handsome.api.domain.project

import com.handsome.api.domain.company.CompanyId
import com.handsome.api.domain.employee.EmployeeId
import java.time.LocalDateTime

interface ProjectRepository {
    fun createProject(project: Project)
    fun findProject(companyId: CompanyId, projectId: ProjectId): Project?
    fun findProjects(companyId: CompanyId, projectIds: List<ProjectId>): List<Project>
    fun findProjectAssignees(companyId: CompanyId, projectId: ProjectId): List<ProjectAssignment>
    fun findEmployeeProjects(companyId: CompanyId, employeeId: EmployeeId): List<Project>
    fun isAssignedToProject(companyId: CompanyId, projectId: ProjectId, employeeId: EmployeeId): Boolean
    fun assignToProject(projectAssignment: ProjectAssignment)
    fun removeFromProject(companyId: CompanyId, projectId: ProjectId, employeeId: EmployeeId, deletedAt: LocalDateTime)
    fun deleteProject(companyId: CompanyId, projectId: ProjectId, deletedAt: LocalDateTime)
}
