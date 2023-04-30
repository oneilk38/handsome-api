package com.handsome.api.infratructure.postgres.ticket

import com.handsome.api.domain.company.CompanyId
import com.handsome.api.domain.employee.EmployeeId
import com.handsome.api.domain.project.ProjectId
import com.handsome.api.domain.ticket.Ticket
import com.handsome.api.domain.ticket.TicketId
import com.handsome.api.domain.ticket.TicketRepository
import com.handsome.api.domain.ticket.TicketStatus
import org.jooq.DSLContext
import org.jooq.generated.tables.Tickets.TICKETS
import org.jooq.generated.tables.records.TicketsRecord
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class PostgresTicketRepository(
    private val dslContext: DSLContext
) : TicketRepository {

    override fun create(ticket: Ticket) {
        dslContext.insertInto(TICKETS).set(ticket.toTicketRecord()).execute()
    }

    override fun find(companyId: CompanyId, ticketId: TicketId): Ticket? {
        return dslContext.selectFrom(TICKETS)
            .where(TICKETS.COMPANY_ID.eq(companyId.value))
            .and(TICKETS.ID.eq(ticketId.value))
            .fetchOne()?.toTicket()
    }

    override fun findAll(companyId: CompanyId, ticketIds: List<TicketId>): List<Ticket> {
        var query =
            dslContext.selectFrom(TICKETS)
                .where(TICKETS.COMPANY_ID.eq(companyId.value))

        if (ticketIds.isNotEmpty()) {
            query = query.and(TICKETS.ID.`in`(ticketIds.map { it.value }))
        }

        return query.fetch().map { it.toTicket() }
    }

    override fun findTicketsForProject(companyId: CompanyId, projectId: ProjectId): List<Ticket> {
        return dslContext.selectFrom(TICKETS)
            .where(TICKETS.COMPANY_ID.eq(companyId.value))
            .and(TICKETS.PROJECT_ID.eq(projectId.value))
            .fetch().map { it.toTicket() }
    }

    override fun findTicketsForEmployee(companyId: CompanyId, employeeId: EmployeeId): List<Ticket> {
        return dslContext.selectFrom(TICKETS)
            .where(TICKETS.COMPANY_ID.eq(companyId.value))
            .and(TICKETS.ASSIGNEE.eq(employeeId.value))
            .fetch().map { it.toTicket() }
    }

    override fun delete(companyId: CompanyId, ticketId: TicketId, deletedAt: LocalDateTime) {
        dslContext.update(TICKETS)
            .set(TICKETS.DELETED_AT, deletedAt)
            .where(TICKETS.COMPANY_ID.eq(companyId.value))
            .and(TICKETS.ID.eq(ticketId.value))
            .execute()
    }

    override fun setAssignee(
        companyId: CompanyId,
        ticketId: TicketId,
        assigneeId: EmployeeId?,
        updatedAt: LocalDateTime
    ) {
        dslContext.update(TICKETS)
            .set(TICKETS.ASSIGNEE, assigneeId?.value)
            .set(TICKETS.UPDATED_AT, updatedAt)
            .where(TICKETS.COMPANY_ID.eq(companyId.value))
            .and(TICKETS.ID.eq(ticketId.value))
            .execute()
    }

    override fun setReporter(companyId: CompanyId, ticketId: TicketId, reporterId: EmployeeId, updatedAt: LocalDateTime) {
        dslContext.update(TICKETS)
            .set(TICKETS.ASSIGNEE, reporterId.value)
            .set(TICKETS.UPDATED_AT, updatedAt)
            .where(TICKETS.COMPANY_ID.eq(companyId.value))
            .and(TICKETS.ID.eq(ticketId.value))
            .execute()
    }

    override fun updateTitle(companyId: CompanyId, ticketId: TicketId, updatedTitle: String, updatedAt: LocalDateTime) {
        dslContext.update(TICKETS)
            .set(TICKETS.TITLE, updatedTitle)
            .set(TICKETS.UPDATED_AT, updatedAt)
            .where(TICKETS.COMPANY_ID.eq(companyId.value))
            .and(TICKETS.ID.eq(ticketId.value))
            .execute()
    }

    override fun updateDescription(companyId: CompanyId, ticketId: TicketId, updatedDescription: String?, updatedAt: LocalDateTime) {
        dslContext.update(TICKETS)
            .set(TICKETS.DESCRIPTION, updatedDescription)
            .set(TICKETS.UPDATED_AT, updatedAt)
            .where(TICKETS.COMPANY_ID.eq(companyId.value))
            .and(TICKETS.ID.eq(ticketId.value))
            .execute()
    }

    override fun setStatus(companyId: CompanyId, ticketId: TicketId, status: TicketStatus, updatedAt: LocalDateTime) {
        dslContext.update(TICKETS)
            .set(TICKETS.STATUS, status.value)
            .set(TICKETS.UPDATED_AT, updatedAt)
            .where(TICKETS.COMPANY_ID.eq(companyId.value))
            .and(TICKETS.ID.eq(ticketId.value))
            .execute()
    }

    private fun TicketsRecord.toTicket() = Ticket(
        id = TicketId(this[TICKETS.ID]),
        companyId = CompanyId(this[TICKETS.COMPANY_ID]),
        projectId = ProjectId(this[TICKETS.PROJECT_ID]),
        assignee = EmployeeId(this[TICKETS.ASSIGNEE]),
        reporter = EmployeeId(this[TICKETS.REPORTER]),
        title = this[TICKETS.TITLE],
        description = this[TICKETS.DESCRIPTION],
        status = TicketStatus.fromDbValue(this[TICKETS.STATUS]),
        createdAt = this[TICKETS.CREATED_AT],
        updatedAt = this[TICKETS.UPDATED_AT],
        deletedAt = this[TICKETS.DELETED_AT]
    )
}
