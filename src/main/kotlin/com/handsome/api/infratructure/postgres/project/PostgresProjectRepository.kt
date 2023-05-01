package com.handsome.api.infratructure.postgres.project

import com.handsome.api.domain.company.CompanyId
import com.handsome.api.domain.employee.EmployeeId
import com.handsome.api.domain.project.Project
import com.handsome.api.domain.project.ProjectAssignment
import com.handsome.api.domain.project.ProjectAssignmentId
import com.handsome.api.domain.project.ProjectId
import com.handsome.api.domain.project.ProjectRepository
import org.jooq.DSLContext
import org.jooq.generated.tables.ProjectAssignments.PROJECT_ASSIGNMENTS
import org.jooq.generated.tables.Projects.PROJECTS
import org.jooq.generated.tables.records.ProjectAssignmentsRecord
import org.jooq.generated.tables.records.ProjectsRecord
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class PostgresProjectRepository(
    private val dslContext: DSLContext
) : ProjectRepository {
    override fun createProject(project: Project) {
        dslContext.insertInto(PROJECTS).set(project.toProjectRecord()).execute()
    }

    override fun findProject(companyId: CompanyId, projectId: ProjectId): Project? {
        return dslContext.selectFrom(PROJECTS)
            .where(PROJECTS.ID.eq(projectId.value))
            .and(PROJECTS.COMPANY_ID.eq(companyId.value))
            .fetchOne()?.toProject()
    }

    override fun findProjects(companyId: CompanyId, projectIds: List<ProjectId>): List<Project> {
        var query =
            dslContext.selectFrom(PROJECTS)
                .where(PROJECTS.COMPANY_ID.eq(companyId.value))
                .and(PROJECTS.DELETED_AT.isNull)

        if (projectIds.isNotEmpty()) {
            query = query.and(PROJECTS.ID.`in`(projectIds.map { it.value }))
        }

        return query.fetch().map { it.toProject() }
    }

    override fun findProjectAssignees(companyId: CompanyId, projectId: ProjectId): List<ProjectAssignment> {
        return dslContext.selectFrom(PROJECT_ASSIGNMENTS)
            .where(PROJECT_ASSIGNMENTS.PROJECT_ID.eq(projectId.value))
            .and(PROJECT_ASSIGNMENTS.COMPANY_ID.eq(companyId.value))
            .fetch().map { it.toProjectAssignment() }
    }

    override fun findEmployeeProjects(companyId: CompanyId, employeeId: EmployeeId): List<Project> {
        return dslContext.select()
            .from(PROJECTS).join(PROJECT_ASSIGNMENTS)
            .on(PROJECTS.ID.eq(PROJECT_ASSIGNMENTS.PROJECT_ID))
            .where(PROJECT_ASSIGNMENTS.EMPLOYEE_ID.eq(employeeId.value))
            .and(PROJECT_ASSIGNMENTS.DELETED_AT.isNull)
            .fetch()
            .into(PROJECTS)
            .map { it.toProject() }
    }

    override fun isAssignedToProject(companyId: CompanyId, projectId: ProjectId, employeeId: EmployeeId): Boolean {
        return dslContext.fetchExists(
            dslContext.selectFrom(PROJECT_ASSIGNMENTS)
                .where(PROJECT_ASSIGNMENTS.COMPANY_ID.eq(companyId.value))
                .and(PROJECT_ASSIGNMENTS.EMPLOYEE_ID.eq(employeeId.value))
                .and(PROJECT_ASSIGNMENTS.DELETED_AT.isNull)
        )
    }

    override fun assignToProject(projectAssignment: ProjectAssignment) {
        if (!isAssignedToProject(
                companyId = projectAssignment.companyId,
                projectId = projectAssignment.projectId,
                employeeId = projectAssignment.employeeId
            )
        ) {
            dslContext.insertInto(PROJECT_ASSIGNMENTS).set(projectAssignment.toProjectAssignmentRecord()).execute()
        }
    }

    override fun removeFromProject(companyId: CompanyId, projectId: ProjectId, employeeId: EmployeeId, deletedAt: LocalDateTime) {
        dslContext.update(PROJECT_ASSIGNMENTS)
            .set(PROJECT_ASSIGNMENTS.DELETED_AT, deletedAt)
            .where(PROJECT_ASSIGNMENTS.COMPANY_ID.eq(companyId.value))
            .and(PROJECT_ASSIGNMENTS.EMPLOYEE_ID.eq(employeeId.value))
            .execute()
    }

    override fun deleteProject(companyId: CompanyId, projectId: ProjectId, deletedAt: LocalDateTime) {
        // delete project from projects table
        dslContext.update(PROJECTS)
            .set(PROJECTS.DELETED_AT, deletedAt)
            .where(PROJECTS.COMPANY_ID.eq(companyId.value))
            .and(PROJECTS.ID.eq(projectId.value))
            .execute()

        // delete assignments also
        dslContext.update(PROJECT_ASSIGNMENTS)
            .set(PROJECT_ASSIGNMENTS.DELETED_AT, deletedAt)
            .where(PROJECT_ASSIGNMENTS.COMPANY_ID.eq(companyId.value))
            .and(PROJECT_ASSIGNMENTS.PROJECT_ID.eq(projectId.value))
            .execute()
    }

    private fun ProjectsRecord.toProject() = Project(
        id = ProjectId(this[PROJECTS.ID]),
        companyId = CompanyId(this[PROJECTS.COMPANY_ID]),
        projectName = this[PROJECTS.PROJECT_NAME],
        projectDescription = this[PROJECTS.PROJECT_DESCRIPTION],
        projectOwner = EmployeeId(this[PROJECTS.PROJECT_OWNER]),
        createdBy = EmployeeId(this[PROJECTS.CREATED_BY]),
        createdAt = this[PROJECTS.CREATED_AT],
        deletedAt = this[PROJECTS.DELETED_AT]
    )

    private fun ProjectAssignmentsRecord.toProjectAssignment() = ProjectAssignment(
        id = ProjectAssignmentId(this[PROJECT_ASSIGNMENTS.ID]),
        projectId = ProjectId(this[PROJECT_ASSIGNMENTS.PROJECT_ID]),
        companyId = CompanyId(this[PROJECT_ASSIGNMENTS.COMPANY_ID]),
        employeeId = EmployeeId(this[PROJECT_ASSIGNMENTS.EMPLOYEE_ID]),
        createdAt = this[PROJECT_ASSIGNMENTS.CREATED_AT],
        deletedAt = this[PROJECT_ASSIGNMENTS.DELETED_AT]
    )
}
