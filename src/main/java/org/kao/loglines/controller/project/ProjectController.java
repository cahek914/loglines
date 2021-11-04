package org.kao.loglines.controller.project;

import lombok.RequiredArgsConstructor;
import org.kao.loglines.controller.GenericCRUDController;
import org.kao.loglines.dto.project.ProjectFullDto;
import org.kao.loglines.dto.project.ProjectUpdateDto;
import org.kao.loglines.entity.project.Project;
import org.kao.loglines.service.GenericCRUDService;
import org.kao.loglines.service.project.ProjectService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController extends GenericCRUDController<Project, ProjectFullDto, ProjectUpdateDto> {

    private final ProjectService projectService;

    @Override
    public GenericCRUDService<Project, ProjectFullDto, ProjectUpdateDto> getService() {
        return projectService;
    }
}
