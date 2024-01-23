package com.handsome.api.domain.ticket

import com.handsome.api.domain.company.CompanyId
import com.handsome.api.domain.employee.EmployeeId
import com.handsome.api.domain.project.ProjectId
import com.handsome.api.http.response.NotFoundException
import java.time.OffsetDateTime

interface TicketRepository {
    fun create(ticket: Ticket)
    fun upsert(ticket: Ticket): Ticket
    fun find(companyId: CompanyId, ticketId: TicketId): Ticket?
    fun findOrFail(companyId: CompanyId, ticketId: TicketId) =
        find(companyId, ticketId)
            ?: throw NotFoundException("Could not find companyId: $companyId, ticketId: $ticketId")
    fun findAll(companyId: CompanyId, ticketIds: List<TicketId>): List<Ticket>
    fun findTicketsForProject(companyId: CompanyId, projectId: ProjectId): List<Ticket>
    fun findTicketsForEmployee(companyId: CompanyId, employeeId: EmployeeId): List<Ticket>
    fun delete(companyId: CompanyId, ticketId: TicketId, deletedAt: OffsetDateTime)
}
