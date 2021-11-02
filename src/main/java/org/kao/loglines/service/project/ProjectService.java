package org.kao.loglines.service.project;

import org.kao.loglines.dto.project.ProjectFullDto;
import org.kao.loglines.dto.project.ProjectUpdateDto;
import org.kao.loglines.entity.project.Project;
import org.kao.loglines.service.GenericCRUDService;

public interface ProjectService extends GenericCRUDService<Project, ProjectFullDto, ProjectUpdateDto> {
}
