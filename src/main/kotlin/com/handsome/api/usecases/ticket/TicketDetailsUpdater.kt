package com.handsome.api.usecases.ticket

import com.handsome.api.common.currentDateTime
import com.handsome.api.domain.company.CompanyId
import com.handsome.api.domain.ticket.Ticket
import com.handsome.api.domain.ticket.TicketId
import com.handsome.api.domain.ticket.TicketRepository
import com.handsome.api.http.response.NotFoundException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class TicketDetailsUpdater(
    private val ticketRepository: TicketRepository
) {
    fun updateDescription(
        companyId: CompanyId,
        ticketId: TicketId,
        description: String?
    ): Ticket {
        val ticket = ticketRepository.find(companyId, ticketId)
            ?: throw NotFoundException("Could not find companyId: $companyId, ticketId: $ticketId")

        return ticketRepository.upsert(
            ticket.copy(
                description = description,
                updatedAt = currentDateTime()
            )
        )
    }

    fun updateTitle(
        companyId: CompanyId,
        ticketId: TicketId,
        title: String
    ): Ticket {
        val ticket = ticketRepository.findOrFail(companyId, ticketId)

        return ticketRepository.upsert(
            ticket.copy(
                title = title,
                updatedAt = currentDateTime()
            )
        )
    }
}
