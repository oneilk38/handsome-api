package com.handsome.api.usecases.ticket

import com.handsome.api.domain.company.CompanyId
import com.handsome.api.domain.employee.EmployeeId
import com.handsome.api.domain.ticket.TicketId
import com.handsome.api.domain.ticket.TicketRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class TicketReporterUpdater(
    private val ticketRepository: TicketRepository
) {
    fun setReporter(
        companyId: CompanyId,
        ticketId: TicketId,
        reporterId: EmployeeId
    ) = ticketRepository.setReporter(
        companyId = companyId,
        ticketId = ticketId,
        reporterId = reporterId,
        updatedAt = LocalDateTime.now()
    )
}
