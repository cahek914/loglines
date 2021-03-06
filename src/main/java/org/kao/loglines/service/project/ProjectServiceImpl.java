package org.kao.loglines.service.project;

import lombok.RequiredArgsConstructor;
import org.kao.loglines.model.project.ProjectFullDto;
import org.kao.loglines.model.project.ProjectUpdateDto;
import org.kao.loglines.entity.project.Project;
import org.kao.loglines.mapper.GenericMapper;
import org.kao.loglines.mapper.project.ProjectMapper;
import org.kao.loglines.repository.ProjectRepository;
import org.kao.loglines.service.GenericCRUDServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl extends GenericCRUDServiceImpl<Project, ProjectFullDto, ProjectUpdateDto> implements ProjectService {

    private final ProjectRepository repository;

    @Autowired
    private ProjectMapper mapper;

    @Override
    public JpaRepository<Project, Long> getRepository() {
        return repository;
    }

    @Override
    public GenericMapper<Project, ProjectFullDto, ProjectUpdateDto> getMapper() {
        return mapper;
    }
}
