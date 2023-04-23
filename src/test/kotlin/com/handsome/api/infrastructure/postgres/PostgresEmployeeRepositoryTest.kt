package com.handsome.api.infrastructure.postgres

import com.handsome.api.domain.EmployeeCreationRequest
import com.handsome.api.domain.EmployeeId
import com.handsome.api.infratructure.postgres.PostgresEmployeeRepository
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
        val employeeId = EmployeeId(1)
        val maybeEmployee = employeeRepository.find(employeeId)

        assertNull(maybeEmployee)
    }

    @Test
    fun `find - should return employee if employee exists`() {
        val employeeCreationRequest = EmployeeCreationRequest(
            firstName = "Kevin",
            lastName = "ONeill",
            email = "kevinoneill@gmail.com",
            position = "Engineer"
        )

        val createdEmployeeId = employeeRepository.create(employeeCreationRequest)

        val employeeFromRepo = employeeRepository.find(createdEmployeeId!!)

        assertEquals(employeeCreationRequest.toEmployee(createdEmployeeId), employeeFromRepo)
    }

    @Test
    fun `findAll - should return all employees`() {
        val employeesToCreate = listOf(
            EmployeeCreationRequest(
                firstName = "Kevin",
                lastName = "ONeill",
                email = "kevinoneill@gmail.com",
                position = "Engineer"
            ),
            EmployeeCreationRequest(
                firstName = "James",
                lastName = "Kelly",
                email = "jkelly@gmail.com",
                position = "Designer"
            ),
            EmployeeCreationRequest(
                firstName = "Sally Anne",
                lastName = "Cash",
                email = "scash@gmail.com",
                position = "Solicitor"
            )
        )

        employeesToCreate.forEach { employeeRepository.create(it) }

        val employeesFromRepo = employeeRepository.findAll()

        val expectedEmployees = listOf(
            employeesToCreate[0].toEmployee(EmployeeId(1)),
            employeesToCreate[1].toEmployee(EmployeeId(2)),
            employeesToCreate[2].toEmployee(EmployeeId(3))
        )

        assertEquals(expectedEmployees, employeesFromRepo)
    }
}
