package com.handsome.api.usecases.project

import com.handsome.api.domain.project.Project
import com.handsome.api.domain.project.ProjectRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class ProjectCreatorUseCase(
    private val projectRepository: ProjectRepository
) {
    fun createProject(project: Project) = projectRepository.createProject(project)
}
