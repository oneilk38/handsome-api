package com.handsome.api.domain.ticket

import com.handsome.api.domain.company.CompanyId
import com.handsome.api.domain.employee.EmployeeId
import com.handsome.api.domain.project.ProjectId
import java.util.*

data class CreateTicketRequest(
    val projectId: ProjectId,
    val companyId: CompanyId,
    val title: String,
    val description: String?,
    val status: TicketStatus,
    val reporter: EmployeeId,
    val assignee: EmployeeId?
) {
    fun toTicket() = Ticket(
        id = TicketId(UUID.randomUUID()),
        projectId = projectId,
        companyId = companyId,
        title = title,
        description = description,
        status = status,
        reporter = reporter,
        assignee = assignee
    )
}

data class SetReporterRequest(
    val companyId: CompanyId,
    val ticketId: TicketId,
    val reporterId: EmployeeId
)

data class SetAssigneeRequest(
    val companyId: CompanyId,
    val ticketId: TicketId,
    val assigneeId: EmployeeId?
)

data class SetTitleRequest(
    val companyId: CompanyId,
    val ticketId: TicketId,
    val title: String
)

data class SetDescriptionRequest(
    val companyId: CompanyId,
    val ticketId: TicketId,
    val description: String?
)

data class SetStatusRequest(
    val companyId: CompanyId,
    val ticketId: TicketId,
    val status: TicketStatus
)
