package org.kao.loglines.controller.project;

import org.kao.loglines.controller.GenericCRUDControllerTest;
import org.kao.loglines.data.TestDataProvider;
import org.kao.loglines.dto.project.ProjectFullDto;
import org.kao.loglines.dto.project.ProjectUpdateDto;
import org.kao.loglines.entity.project.Project;
import org.kao.loglines.mapper.GenericMapper;
import org.kao.loglines.mapper.project.ProjectMapper;
import org.kao.loglines.service.GenericCRUDService;
import org.kao.loglines.service.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class ProjectControllerTest extends GenericCRUDControllerTest<Project, ProjectFullDto, ProjectUpdateDto> {

    private static final String URL_ROOT = "/projects";
    private static final String URL_ID = "/projects/{id}";

    @Autowired
    private TestDataProvider dataProvider;

    @MockBean
    private ProjectService projectService;

    @Autowired
    private ProjectMapper projectMapper;

    @Override
    protected Class<ProjectFullDto> getDtoFullClass() {
        return ProjectFullDto.class;
    }

    @Override
    protected Class<ProjectUpdateDto> getDtoUpdateClass() {
        return ProjectUpdateDto.class;
    }

    @Override
    protected String getRootUrl() {
        return URL_ROOT;
    }

    @Override
    protected String getUrlWithId() {
        return URL_ID;
    }

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
