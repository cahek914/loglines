package org.kao.loglines.service.project;

import org.kao.loglines.data.TestDataProvider;
import org.kao.loglines.model.project.ProjectFullDto;
import org.kao.loglines.model.project.ProjectUpdateDto;
import org.kao.loglines.entity.project.Project;
import org.kao.loglines.mapper.GenericMapper;
import org.kao.loglines.mapper.project.ProjectMapper;
import org.kao.loglines.service.GenericCRUDService;
import org.kao.loglines.service.GenericCRUDServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProjectServiceTest extends GenericCRUDServiceTest<Project, ProjectFullDto, ProjectUpdateDto> {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TestDataProvider dataProvider;

    @Autowired
    private ProjectMapper projectMapper;

    @Override
    protected Project getEntity() {
        return dataProvider.project(0);
    }

    @Override
    protected Project getEntity(int maxSizeCorrector) {
        return dataProvider.project(maxSizeCorrector);
    }

    @Override
    protected GenericMapper<Project, ProjectFullDto, ProjectUpdateDto> getMapper() {
        return projectMapper;
    }

    @Override
    protected GenericCRUDService<Project, ProjectFullDto, ProjectUpdateDto> getService() {
        return projectService;
    }

}
