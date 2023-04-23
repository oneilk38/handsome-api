package com.handsome.api.infratructure.postgres.employee

import com.handsome.api.domain.CompanyId
import com.handsome.api.domain.Employee
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

    override fun find(employeeId: EmployeeId, companyId: CompanyId): Employee? {
        return dslContext.selectFrom(EMPLOYEES)
            .where(EMPLOYEES.ID.eq(employeeId.value))
            .and(EMPLOYEES.COMPANY_ID.eq(companyId.value))
            .fetchOne()?.toEmployee() ?: null
    }

    override fun findAllByCompany(companyId: CompanyId): List<Employee> {
        return dslContext.selectFrom(EMPLOYEES)
            .where(EMPLOYEES.COMPANY_ID.eq(companyId.value))
            .fetch().map { it.toEmployee() }
    }

    override fun create(employee: Employee): EmployeeId? {
        return dslContext.insertInto(EMPLOYEES)
            .set(EMPLOYEES.ID, employee.id.value)
            .set(EMPLOYEES.COMPANY_ID, employee.companyId.value)
            .set(EMPLOYEES.FIRST_NAME, employee.firstName)
            .set(EMPLOYEES.LAST_NAME, employee.lastName)
            .set(EMPLOYEES.EMAIL, employee.email)
            .set(EMPLOYEES.JOB_TITLE, employee.position)
            .set(EMPLOYEES.CREATED_AT, employee.createdAt)
            .returning(EMPLOYEES.ID)
            .fetchOne()
            .toEmployeeId()
    }

    override fun deleteByCompany(companyId: CompanyId) {
        dslContext.deleteFrom(EMPLOYEES)
            .where(EMPLOYEES.COMPANY_ID.eq(companyId.value))
            .execute()
    }

    private fun EmployeesRecord?.toEmployeeId(): EmployeeId? {
        if (this == null) {
            return null
        }

        return EmployeeId(this.get(EMPLOYEES.ID))
    }

    private fun Record.toEmployee() = Employee(
        id = EmployeeId(this[EMPLOYEES.ID]),
        companyId = CompanyId(this[EMPLOYEES.COMPANY_ID]),
        firstName = this[EMPLOYEES.FIRST_NAME],
        lastName = this[EMPLOYEES.LAST_NAME],
        email = this[EMPLOYEES.EMAIL],
        position = this[EMPLOYEES.JOB_TITLE],
        createdAt = this[EMPLOYEES.CREATED_AT],
        deletedAt = this[EMPLOYEES.DELETED_AT]
    )
}
