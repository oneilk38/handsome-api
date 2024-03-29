package com.handsome.api.domain.project

import com.handsome.api.domain.company.CompanyId
import com.handsome.api.domain.employee.EmployeeId
import org.jooq.generated.tables.records.ProjectsRecord
import java.time.OffsetDateTime
import java.util.UUID

data class ProjectId(val value: UUID) {
    constructor(uuidStr: String) : this(UUID.fromString(uuidStr))

    override fun toString(): String = value.toString()
}

data class Project(
    val id: ProjectId,
    val companyId: CompanyId,
    val projectName: String,
    val projectDescription: String,
    val projectOwner: EmployeeId,
    val createdBy: EmployeeId,
    val createdAt: OffsetDateTime = OffsetDateTime.now(),
    val deletedAt: OffsetDateTime? = null
) {
    fun toProjectRecord(): ProjectsRecord {
        val record = ProjectsRecord()

        record.id = id.value
        record.companyId = companyId.value
        record.projectName = projectName
        record.projectDescription = projectDescription
        record.projectOwner = projectOwner.value
        record.createdBy = createdBy.value
        record.createdAt = createdAt
        record.deletedAt = deletedAt

        return record
    }
}
