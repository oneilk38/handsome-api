package com.handsome.api.domain.project

import com.handsome.api.domain.company.CompanyId
import com.handsome.api.domain.employee.EmployeeId
import org.jooq.generated.tables.records.ProjectAssignmentsRecord
import java.time.OffsetDateTime
import java.util.UUID

data class ProjectAssignmentId(val value: UUID) {
    constructor(uuidStr: String) : this(UUID.fromString(uuidStr))

    override fun toString(): String = value.toString()
}

data class ProjectAssignment(
    val id: ProjectAssignmentId,
    val projectId: ProjectId,
    val companyId: CompanyId,
    val employeeId: EmployeeId,
    val createdAt: OffsetDateTime = OffsetDateTime.now(),
    val deletedAt: OffsetDateTime? = null
) {
    fun toProjectAssignmentRecord(): ProjectAssignmentsRecord {
        val record = ProjectAssignmentsRecord()

        record.id = id.value
        record.projectId = projectId.value
        record.companyId = companyId.value
        record.employeeId = employeeId.value
        record.createdAt = createdAt
        record.deletedAt = deletedAt

        return record
    }
}
