package com.handsome.api.domain.ticket

import com.handsome.api.domain.company.CompanyId
import com.handsome.api.domain.employee.EmployeeId
import com.handsome.api.domain.project.ProjectId
import org.jooq.generated.tables.records.TicketsRecord
import java.time.OffsetDateTime
import java.util.UUID

data class TicketId(val value: UUID) {
    constructor(uuidStr: String) : this(UUID.fromString(uuidStr))

    override fun toString(): String = value.toString()
}

enum class TicketStatus(val value: String) {
    READY("ready"),
    TODO("todo"),
    IN_PROGRESS("in_progress"),
    IN_REVIEW("in_review"),
    RELEASE("release"),
    DONE("done"),
    CLOSED("closed"),
    DELETED("deleted");

    companion object {
        fun fromString(value: String) = when (value) {
            "ready" -> READY
            "todo" -> TODO
            "in_progress" -> IN_PROGRESS
            "in_review" -> IN_REVIEW
            "release" -> RELEASE
            "done" -> DONE
            "closed" -> CLOSED
            else -> DELETED
        }
    }
}

data class Ticket(
    val id: TicketId,
    val projectId: ProjectId,
    val companyId: CompanyId,
    val title: String,
    val description: String?,
    val status: TicketStatus,
    val reporter: EmployeeId,
    val assignee: EmployeeId?,
    val createdAt: OffsetDateTime = OffsetDateTime.now(),
    val updatedAt: OffsetDateTime? = null,
    val deletedAt: OffsetDateTime? = null
) {
    fun toTicketRecord(): TicketsRecord {
        val record = TicketsRecord()

        record.id = id.value
        record.projectId = projectId.value
        record.companyId = companyId.value
        record.title = title
        record.description = description
        record.status = status.value
        record.reporter = reporter.value
        record.assignee = assignee?.value
        record.createdAt = createdAt
        record.updatedAt = updatedAt
        record.deletedAt = deletedAt

        return record
    }
}
