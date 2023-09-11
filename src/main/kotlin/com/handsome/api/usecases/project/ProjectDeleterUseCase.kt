package com.handsome.api.usecases.project

import com.handsome.api.domain.company.CompanyId
import com.handsome.api.domain.project.ProjectId
import com.handsome.api.domain.project.ProjectRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class ProjectDeleterUseCase(
    private val projectRepository: ProjectRepository
) {
    fun deleteProject(companyId: CompanyId, projectId: ProjectId) = projectRepository.deleteProject(
        companyId = companyId,
        projectId = projectId,
        deletedAt = LocalDateTime.now()
    )
}
