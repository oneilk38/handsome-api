package com.handsome.api.infrastructure.postgres

import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
open class DatabaseTest {

    companion object {
        private const val testDbName = "root"
        private const val testUser = "root"
        private const val testPw = "password"
        private const val IMAGE_VERSION = "postgres:14"

        val container = PostgreSQLContainer<Nothing>(IMAGE_VERSION).apply {
            withDatabaseName(testDbName)
            withUsername(testUser)
            withPassword(testPw)
            start()
        }

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add(("spring.datasource.url"), container::getJdbcUrl)
            registry.add(("spring.datasource.password"), container::getPassword)
            registry.add(("spring.datasource.username"), container::getUsername)
        }
    }
}
