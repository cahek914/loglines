package org.kao.loglines.mapper.project;

import org.kao.loglines.dto.project.ProjectFullDto;
import org.kao.loglines.entity.project.Project;
import org.kao.loglines.mapper.GenericMapperImpl;
import org.kao.loglines.mapper.directory.DirectoryMapper;
import org.kao.loglines.mapper.task.TaskMapper;
import org.kao.loglines.service.GenericCRUDService;
import org.kao.loglines.service.directory.DirectoryService;
import org.kao.loglines.service.project.ProjectService;
import org.kao.loglines.service.task.TaskService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {TaskMapper.class, DirectoryMapper.class, DirectoryService.class, TaskService.class})
public abstract class ProjectMapper extends GenericMapperImpl<Project, ProjectFullDto> {

    @Autowired
    private ProjectService projectService;

    @Override
    public GenericCRUDService<Project, ProjectFullDto> getService() {
        return projectService;
    }

    @Override
    @Mapping(source = "parentDirectory.id", target = "parentDirectoryId")
    @Mapping(source = "tasks", target = "taskIds")
    public abstract ProjectFullDto mapToDto(Project project);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "taskIds", target = "tasks", qualifiedByName = "mapIdsToEntities")
    @Mapping(source = "parentDirectoryId", target = "parentDirectory", qualifiedByName = "mapIdToEntity")
    public abstract Project mapToEntity(ProjectFullDto projectFullDto);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "taskIds", target = "tasks", qualifiedByName = "mapIdsToEntities")
    @Mapping(source = "parentDirectoryId", target = "parentDirectory", qualifiedByName = "mapIdToEntity")
    public abstract Project mapUpdateEntity(@MappingTarget Project project, ProjectFullDto projectFullDto);

}
