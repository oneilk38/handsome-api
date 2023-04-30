package com.handsome.api.domain.ticket

import com.handsome.api.domain.company.CompanyId
import com.handsome.api.domain.employee.EmployeeId
import com.handsome.api.domain.project.ProjectId
import java.time.LocalDateTime

interface TicketRepository {
    fun create(ticket: Ticket)
    fun find(companyId: CompanyId, ticketId: TicketId): Ticket?
    fun findAll(companyId: CompanyId, ticketIds: List<TicketId>): List<Ticket>
    fun findTicketsForProject(companyId: CompanyId, projectId: ProjectId): List<Ticket>
    fun findTicketsForEmployee(companyId: CompanyId, employeeId: EmployeeId): List<Ticket>
    fun delete(companyId: CompanyId, ticketId: TicketId, deletedAt: LocalDateTime)
    fun setAssignee(companyId: CompanyId, ticketId: TicketId, assigneeId: EmployeeId?, updatedAt: LocalDateTime)
    fun setReporter(companyId: CompanyId, ticketId: TicketId, reporterId: EmployeeId, updatedAt: LocalDateTime)
    fun setStatus(companyId: CompanyId, ticketId: TicketId, status: TicketStatus, updatedAt: LocalDateTime)
    fun updateTitle(companyId: CompanyId, ticketId: TicketId, updatedTitle: String, updatedAt: LocalDateTime)
    fun updateDescription(companyId: CompanyId, ticketId: TicketId, updatedDescription: String?, updatedAt: LocalDateTime)
}
