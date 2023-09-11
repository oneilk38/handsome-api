package com.handsome.api.usecases.ticket

import com.handsome.api.domain.company.CompanyId
import com.handsome.api.domain.employee.EmployeeId
import com.handsome.api.domain.project.ProjectId
import com.handsome.api.domain.ticket.TicketId
import com.handsome.api.domain.ticket.TicketRepository
import org.springframework.stereotype.Component

@Component
class TicketFinderUseCase(
    private val ticketRepository: TicketRepository
) {
    fun findTicket(companyId: CompanyId, ticketId: TicketId) = ticketRepository.find(companyId, ticketId)
    fun findTickets(
        companyId: CompanyId,
        ticketIds: List<TicketId> = emptyList()
    ) = ticketRepository.findAll(companyId, ticketIds)
    fun findTicketsForProject(companyId: CompanyId, projectId: ProjectId) =
        ticketRepository.findTicketsForProject(companyId, projectId)
    fun findTicketsForEmployee(companyId: CompanyId, employeeId: EmployeeId) =
        ticketRepository.findTicketsForEmployee(companyId, employeeId)
}
