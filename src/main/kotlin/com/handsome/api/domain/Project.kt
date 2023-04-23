package com.handsome.api.domain

import java.time.LocalDateTime
import java.util.UUID

data class ProjectId(val value: UUID) {
    constructor(uuidStr: String) : this(UUID.fromString(uuidStr))
    override fun toString(): String = value.toString()
}

data class Project(
    val id: ProjectId,
    val companyId: CompanyId,
    val projectName: String,
    val projectDescription: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val deletedAt: LocalDateTime? = null
)
