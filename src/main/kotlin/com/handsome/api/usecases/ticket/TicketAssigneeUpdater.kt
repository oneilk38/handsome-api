package com.handsome.api.usecases.ticket

import com.handsome.api.common.currentDateTime
import com.handsome.api.domain.company.CompanyId
import com.handsome.api.domain.employee.EmployeeId
import com.handsome.api.domain.ticket.Ticket
import com.handsome.api.domain.ticket.TicketId
import com.handsome.api.domain.ticket.TicketRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class TicketAssigneeUpdater(
    private val ticketRepository: TicketRepository
) {
    fun setAssignee(
        companyId: CompanyId,
        ticketId: TicketId,
        assigneeId: EmployeeId? = null
    ): Ticket {
        val ticket = ticketRepository.findOrFail(companyId, ticketId)

        return ticketRepository.upsert(
            ticket.copy(
                assignee = assigneeId,
                updatedAt = currentDateTime()
            )
        )
    }
}
