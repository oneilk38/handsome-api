package com.handsome.api.http

import com.handsome.api.domain.company.CompanyId
import com.handsome.api.domain.employee.EmployeeId
import com.handsome.api.domain.project.CreateProjectAssignmentRequest
import com.handsome.api.domain.project.CreateProjectRequest
import com.handsome.api.domain.project.Project
import com.handsome.api.domain.project.ProjectId
import com.handsome.api.domain.project.RemoveProjectAssignmentRequest
import com.handsome.api.usecases.project.ProjectAssignorUseCase
import com.handsome.api.usecases.project.ProjectCreatorUseCase
import com.handsome.api.usecases.project.ProjectDeleterUseCase
import com.handsome.api.usecases.project.ProjectFinderUseCase
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class ProjectController(
    private val projectAssignorUseCase: ProjectAssignorUseCase,
    private val projectCreatorUseCase: ProjectCreatorUseCase,
    private val projectDeleterUseCase: ProjectDeleterUseCase,
    private val projectFinderUseCase: ProjectFinderUseCase
) {
    @PostMapping("/projects")
    fun createProject(@RequestBody request: CreateProjectRequest) {
        projectCreatorUseCase.createProject(request.toProject())
    }

    @GetMapping("/projects/{projectId}/company/{companyId}")
    fun getProject(@PathVariable companyId: UUID, @PathVariable projectId: UUID): Project? {
        return projectFinderUseCase.findProject(
            companyId = CompanyId(companyId),
            projectId = ProjectId(projectId)
        )
    }

    @GetMapping("/projects/company/{companyId}")
    fun getProjects(@PathVariable companyId: UUID): List<Project> {
        return projectFinderUseCase.findProjects(
            companyId = CompanyId(companyId)
        )
    }

    @GetMapping("/projects/company/{companyId}/employee/{employeeId}")
    fun getProjectsForEmployee(@PathVariable companyId: UUID, @PathVariable employeeId: UUID): List<Project> {
        return projectFinderUseCase.findEmployeeProjects(
            companyId = CompanyId(companyId),
            employeeId = EmployeeId(employeeId)
        )
    }

    @PutMapping("/projects/assign")
    fun assign(@RequestBody request: CreateProjectAssignmentRequest) {
        projectAssignorUseCase.assign(request.toProjectAssignment())
    }

    @PutMapping("/projects/remove")
    fun remove(@RequestBody request: RemoveProjectAssignmentRequest) {
        projectAssignorUseCase.remove(
            companyId = request.companyId,
            projectId = request.projectId,
            employeeId = request.employeeId
        )
    }

    @DeleteMapping("/projects/{projectId}/company/{companyId}")
    fun deleteProject(@PathVariable companyId: UUID, @PathVariable projectId: UUID) {
        projectDeleterUseCase.deleteProject(
            companyId = CompanyId(companyId),
            projectId = ProjectId(projectId)
        )
    }
}
