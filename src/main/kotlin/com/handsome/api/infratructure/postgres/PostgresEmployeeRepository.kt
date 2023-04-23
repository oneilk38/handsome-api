package com.handsome.api.infratructure.postgres

import com.handsome.api.domain.Employee
import com.handsome.api.domain.EmployeeCreationRequest
import com.handsome.api.domain.EmployeeId
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.generated.tables.Employees.EMPLOYEES
import org.jooq.generated.tables.records.EmployeesRecord
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class PostgresEmployeeRepository(
    private val dslContext: DSLContext
) : EmployeeRepository {

    override fun find(employeeId: EmployeeId): Employee? {
        return dslContext.selectFrom(EMPLOYEES)
            .where(EMPLOYEES.ID.eq(employeeId.value.toInt()))
            .fetchOne()?.toEmployee() ?: null
    }

    override fun findAll(): List<Employee> {
        return dslContext.selectFrom(EMPLOYEES).fetch().map { it.toEmployee() }
    }

    override fun create(employee: EmployeeCreationRequest): EmployeeId? {
        return dslContext.insertInto(EMPLOYEES)
            .set(EMPLOYEES.FIRST_NAME, employee.firstName)
            .set(EMPLOYEES.LAST_NAME, employee.lastName)
            .set(EMPLOYEES.EMAIL, employee.email)
            .set(EMPLOYEES.JOB_TITLE, employee.position)
            .returning(EMPLOYEES.ID)
            .fetchOne()
            .toEmployeeId()
    }

    private fun EmployeesRecord?.toEmployeeId(): EmployeeId? {
        if (this == null) {
            return null
        }

        return this.get(EMPLOYEES.ID).let { EmployeeId.fromInt(it) }
    }

    private fun Record.toEmployee() = Employee(
        id = EmployeeId.fromInt(this[EMPLOYEES.ID]),
        firstName = this[EMPLOYEES.FIRST_NAME],
        lastName = this[EMPLOYEES.LAST_NAME],
        email = this[EMPLOYEES.EMAIL],
        position = this[EMPLOYEES.JOB_TITLE]
    )
}
