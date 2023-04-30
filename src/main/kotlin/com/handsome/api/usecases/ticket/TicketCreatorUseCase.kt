package com.handsome.api.usecases.ticket

import com.handsome.api.domain.ticket.Ticket
import com.handsome.api.domain.ticket.TicketRepository
import org.springframework.stereotype.Component

@Component
class TicketCreatorUseCase(
    private val ticketRepository: TicketRepository
) {
    fun createTicket(ticket: Ticket) = ticketRepository.create(ticket)
}
