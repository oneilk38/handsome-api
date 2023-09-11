package com.handsome.api.usecases.ticket

import com.handsome.api.domain.company.CompanyId
import com.handsome.api.domain.ticket.TicketId
import com.handsome.api.domain.ticket.TicketRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class TicketDeleterUseCase(
    private val ticketRepository: TicketRepository
) {
    fun deleteTicket(
        companyId: CompanyId,
        ticketId: TicketId
    ) = ticketRepository.delete(
        companyId = companyId,
        ticketId = ticketId,
        deletedAt = LocalDateTime.now()
    )
}
