package com.handsome.api.http

import com.handsome.api.domain.company.CompanyId
import com.handsome.api.domain.employee.EmployeeId
import com.handsome.api.domain.project.ProjectId
import com.handsome.api.domain.ticket.CreateTicketRequest
import com.handsome.api.domain.ticket.SetAssigneeRequest
import com.handsome.api.domain.ticket.SetDescriptionRequest
import com.handsome.api.domain.ticket.SetReporterRequest
import com.handsome.api.domain.ticket.SetStatusRequest
import com.handsome.api.domain.ticket.SetTitleRequest
import com.handsome.api.domain.ticket.Ticket
import com.handsome.api.domain.ticket.TicketId
import com.handsome.api.domain.ticket.TicketStatus
import com.handsome.api.usecases.ticket.TicketAssigneeUpdater
import com.handsome.api.usecases.ticket.TicketCreatorUseCase
import com.handsome.api.usecases.ticket.TicketDeleterUseCase
import com.handsome.api.usecases.ticket.TicketDetailsUpdater
import com.handsome.api.usecases.ticket.TicketFinderUseCase
import com.handsome.api.usecases.ticket.TicketReporterUpdater
import com.handsome.api.usecases.ticket.TicketStatusUpdater
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class TicketController(
    private val ticketFinderUseCase: TicketFinderUseCase,
    private val ticketCreatorUseCase: TicketCreatorUseCase,
    private val ticketAssigneeUpdater: TicketAssigneeUpdater,
    private val ticketDeleterUseCase: TicketDeleterUseCase,
    private val ticketReporterUpdater: TicketReporterUpdater,
    private val ticketDetailsUpdater: TicketDetailsUpdater,
    private val ticketStatusUpdater: TicketStatusUpdater
) {
    @PostMapping("/tickets")
    fun create(@RequestBody createTicketRequest: CreateTicketRequest) {
        ticketCreatorUseCase.createTicket(createTicketRequest.toTicket())
    }

    @GetMapping("/tickets/company/{companyId}")
    fun getTickets(@PathVariable companyId: UUID): List<Ticket> {
        return ticketFinderUseCase.findTickets(CompanyId(companyId))
    }

    @GetMapping("/tickets/company/{companyId}/employee/{employeeId}")
    fun getEmployeeTickets(@PathVariable companyId: UUID, @PathVariable employeeId: UUID): List<Ticket> {
        return ticketFinderUseCase.findTicketsForEmployee(
            companyId = CompanyId(companyId),
            employeeId = EmployeeId(employeeId)
        )
    }

    @GetMapping("/tickets/company/{companyId}/ticket/{ticketId}")
    fun getTicket(@PathVariable companyId: UUID, @PathVariable ticketId: UUID): Ticket? {
        return ticketFinderUseCase.findTicket(
            companyId = CompanyId(companyId),
            ticketId = TicketId(ticketId)
        )
    }

    @GetMapping("/tickets/company/{companyId}/project/{projectId}")
    fun getTicketsForProject(@PathVariable companyId: UUID, @PathVariable projectId: UUID): List<Ticket> {
        return ticketFinderUseCase.findTicketsForProject(
            companyId = CompanyId(companyId),
            projectId = ProjectId(projectId)
        )
    }

    @PutMapping("/tickets/assign")
    fun assignToTicket(@RequestBody request: SetAssigneeRequest) {
        ticketAssigneeUpdater.setAssignee(
            companyId = request.companyId,
            ticketId = request.ticketId,
            assigneeId = request.assigneeId
        )
    }

    @PutMapping("/tickets/unassign")
    fun removeFromTicket(@RequestBody request: SetAssigneeRequest) {
        ticketAssigneeUpdater.setAssignee(
            companyId = request.companyId,
            ticketId = request.ticketId
        )
    }

    @PutMapping("/tickets/reporter")
    fun setReporter(@RequestBody request: SetReporterRequest) {
        ticketReporterUpdater.setReporter(
            companyId = request.companyId,
            ticketId = request.ticketId,
            reporterId = request.reporterId
        )
    }

    @PutMapping("/tickets/status")
    fun setStatus(@RequestBody request: SetStatusRequest) {
        ticketStatusUpdater.setStatus(
            companyId = request.companyId,
            ticketId = request.ticketId,
            status = TicketStatus.fromString(request.status)
        )
    }

    @PutMapping("/tickets/title")
    fun setTitle(@RequestBody request: SetTitleRequest) {
        ticketDetailsUpdater.updateTitle(
            companyId = request.companyId,
            ticketId = request.ticketId,
            title = request.title
        )
    }

    @PutMapping("/tickets/description")
    fun setDescription(@RequestBody request: SetDescriptionRequest) {
        ticketDetailsUpdater.updateDescription(
            companyId = request.companyId,
            ticketId = request.ticketId,
            description = request.description
        )
    }

    @DeleteMapping("/tickets/company/{companyId}/ticket/{ticketId}")
    fun deleteTicket(@PathVariable companyId: UUID, @PathVariable ticketId: UUID) {
        ticketDeleterUseCase.deleteTicket(
            companyId = CompanyId(companyId),
            ticketId = TicketId(ticketId)
        )
    }
}
