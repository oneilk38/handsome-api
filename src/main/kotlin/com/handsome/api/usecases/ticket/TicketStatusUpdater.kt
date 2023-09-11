package com.handsome.api.usecases.ticket

import com.handsome.api.domain.company.CompanyId
import com.handsome.api.domain.ticket.TicketId
import com.handsome.api.domain.ticket.TicketRepository
import com.handsome.api.domain.ticket.TicketStatus
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class TicketStatusUpdater(
    private val ticketRepository: TicketRepository
) {
    fun setStatus(companyId: CompanyId, ticketId: TicketId, status: TicketStatus) = ticketRepository.setStatus(
        companyId = companyId,
        ticketId = ticketId,
        status = status,
        updatedAt = LocalDateTime.now()
    )
}
