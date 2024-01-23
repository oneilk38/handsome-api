package com.handsome.api.usecases.project

import com.handsome.api.domain.company.CompanyId
import com.handsome.api.domain.project.ProjectId
import com.handsome.api.domain.project.ProjectRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime

@Component
@Transactional
class ProjectDeleterUseCase(
    private val projectRepository: ProjectRepository
) {
    fun deleteProject(companyId: CompanyId, projectId: ProjectId) = projectRepository.OffsetDateTime(
        companyId = companyId,
        projectId = projectId,
        deletedAt = OffsetDateTime.now()
    )
}
