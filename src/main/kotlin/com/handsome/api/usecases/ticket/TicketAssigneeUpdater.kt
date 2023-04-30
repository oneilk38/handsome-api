package com.handsome.api.usecases.ticket

import com.handsome.api.domain.company.CompanyId
import com.handsome.api.domain.employee.EmployeeId
import com.handsome.api.domain.ticket.TicketId
import com.handsome.api.domain.ticket.TicketRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class TicketAssigneeUpdater(
    private val ticketRepository: TicketRepository
) {
    fun assignToTicket(
        companyId: CompanyId,
        ticketId: TicketId,
        assigneeId: EmployeeId?
    ) = ticketRepository.setAssignee(
        companyId = companyId,
        ticketId = ticketId,
        assigneeId = assigneeId,
        updatedAt = LocalDateTime.now()
    )

    fun removeFromTicket(
        companyId: CompanyId,
        ticketId: TicketId,
        assigneeId: EmployeeId?
    ) = ticketRepository.setAssignee(
        companyId = companyId,
        ticketId = ticketId,
        assigneeId = null,
        updatedAt = LocalDateTime.now()
    )
}
