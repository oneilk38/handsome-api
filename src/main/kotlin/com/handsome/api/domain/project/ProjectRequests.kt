package com.handsome.api.domain.project

import com.handsome.api.domain.company.CompanyId
import com.handsome.api.domain.employee.EmployeeId
import java.util.*

data class CreateProjectRequest(
    val companyId: CompanyId,
    val projectName: String,
    val projectDescription: String,
    val projectOwner: EmployeeId,
    val createdBy: EmployeeId
) {
    fun toProject() = Project(
        id = ProjectId(UUID.randomUUID()),
        companyId = companyId,
        projectName = projectName,
        projectDescription = projectDescription,
        projectOwner = projectOwner,
        createdBy = createdBy
    )
}

data class CreateProjectAssignmentRequest(
    val projectId: ProjectId,
    val companyId: CompanyId,
    val employeeId: EmployeeId
) {
    fun toProjectAssignment() = ProjectAssignment(
        id = ProjectAssignmentId(UUID.randomUUID()),
        projectId = projectId,
        companyId = companyId,
        employeeId = employeeId
    )
}

data class RemoveProjectAssignmentRequest(
    val projectId: ProjectId,
    val companyId: CompanyId,
    val employeeId: EmployeeId
)
