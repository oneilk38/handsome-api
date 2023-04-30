package com.handsome.api.usecases.ticket

import com.handsome.api.domain.company.CompanyId
import com.handsome.api.domain.ticket.TicketId
import com.handsome.api.domain.ticket.TicketRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class TicketDetailsUpdater(
    private val ticketRepository: TicketRepository
) {
    fun updateDescription(
        companyId: CompanyId,
        ticketId: TicketId,
        description: String?
    ) = ticketRepository.updateDescription(
        companyId = companyId,
        ticketId = ticketId,
        updatedDescription = description,
        updatedAt = LocalDateTime.now()
    )

    fun updateTitle(
        companyId: CompanyId,
        ticketId: TicketId,
        title: String
    ) = ticketRepository.updateTitle(
        companyId = companyId,
        ticketId = ticketId,
        updatedTitle = title,
        updatedAt = LocalDateTime.now()
    )
}
