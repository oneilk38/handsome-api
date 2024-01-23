package com.handsome.api.usecases.ticket

import com.handsome.api.common.currentDateTime
import com.handsome.api.domain.company.CompanyId
import com.handsome.api.domain.ticket.Ticket
import com.handsome.api.domain.ticket.TicketId
import com.handsome.api.domain.ticket.TicketRepository
import com.handsome.api.domain.ticket.TicketStatus
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class TicketStatusUpdater(
    private val ticketRepository: TicketRepository
) {
    fun setStatus(companyId: CompanyId, ticketId: TicketId, status: TicketStatus): Ticket {
        val ticket = ticketRepository.findOrFail(companyId, ticketId)

        return ticketRepository.upsert(
            ticket.copy(
                status = status,
                updatedAt = currentDateTime()
            )
        )
    }
}
