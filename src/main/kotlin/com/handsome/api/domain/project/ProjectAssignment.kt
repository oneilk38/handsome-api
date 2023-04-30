package com.handsome.api.domain.project

import com.handsome.api.domain.company.CompanyId
import com.handsome.api.domain.employee.EmployeeId
import org.jooq.generated.tables.records.ProjectAssignmentsRecord
import java.time.LocalDateTime
import java.util.UUID

data class ProjectAssignmentId(val value: UUID)

data class ProjectAssignment(
    val id: ProjectAssignmentId,
    val projectId: ProjectId,
    val companyId: CompanyId,
    val employeeId: EmployeeId,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val deletedAt: LocalDateTime? = null
) {
    fun toProjectAssignmentRecord(): ProjectAssignmentsRecord {
        val record = ProjectAssignmentsRecord()

        record.id = id.value
        record.projectId = projectId.value
        record.companyId = companyId.value
        record.employeeId = employeeId.value
        record.createdAt = createdAt
        record.deletedAt

        return record
    }
}
