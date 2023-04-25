package com.handsome.api.infrastructure.postgres

import com.handsome.api.domain.CompanyId
import com.handsome.api.domain.Employee
import com.handsome.api.domain.EmployeeId
import com.handsome.api.infratructure.postgres.employee.PostgresEmployeeRepository
import org.jooq.DSLContext
import org.jooq.generated.tables.Employees
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
@SpringBootTest(
    classes = [MyTestConfig::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class PostgresEmployeeRepositoryTest(
    @Autowired val dslContext: DSLContext
) : DatabaseTest() {

    private lateinit var employeeRepository: PostgresEmployeeRepository

    @BeforeEach
    fun setUp() {
        dslContext.truncate(Employees.EMPLOYEES).execute()
        employeeRepository = PostgresEmployeeRepository(dslContext)
    }

    @Test
    fun `find - should return null if no employee exists`() {
        val employeeId = EmployeeId(UUID.randomUUID())
        val companyId = CompanyId(UUID.randomUUID())
        val maybeEmployee = employeeRepository.find(employeeId, companyId)

        assertNull(maybeEmployee)
    }

    @Test
    fun `find - should return employee if employee exists`() {
        val employeeToCreate = Employee(
            id = EmployeeId(UUID.randomUUID()),
            companyId = CompanyId(UUID.randomUUID()),
            firstName = "Kevin",
            lastName = "ONeill",
            email = "kevinoneill@gmail.com",
            position = "Engineer"
        )

        val createdEmployeeId = employeeRepository.create(employeeToCreate)

        val employeeFromRepo = employeeRepository.find(createdEmployeeId!!, employeeToCreate.companyId)

        assertEquals(employeeToCreate, employeeFromRepo)
    }

    @Test
    fun `findAll - should return all employees`() {
        val companyId = CompanyId(UUID.randomUUID())

        val employeesToCreate = listOf(
            Employee(
                id = EmployeeId(UUID.randomUUID()),
                companyId = companyId,
                firstName = "Kevin",
                lastName = "ONeill",
                email = "kevinoneill@gmail.com",
                position = "Engineer"
            ),
            Employee(
                id = EmployeeId(UUID.randomUUID()),
                companyId = companyId,
                firstName = "James",
                lastName = "Kelly",
                email = "jkelly@gmail.com",
                position = "Designer"
            ),
            Employee(
                id = EmployeeId(UUID.randomUUID()),
                companyId = companyId,
                firstName = "Sally Anne",
                lastName = "Cash",
                email = "scash@gmail.com",
                position = "Solicitor"
            )
        )

        employeesToCreate.forEach { employeeRepository.create(it) }

        val employeesFromRepo = employeeRepository.findAllByCompany(companyId)

        assertEquals(employeesToCreate, employeesFromRepo)
    }
}
