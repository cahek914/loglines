package org.kao.loglines.mapper.project;

import org.kao.loglines.model.project.ProjectFullDto;
import org.kao.loglines.model.project.ProjectUpdateDto;
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
public abstract class ProjectMapper extends GenericMapperImpl<Project, ProjectFullDto, ProjectUpdateDto> {

    @Autowired
    private ProjectService projectService;

    @Override
    protected GenericCRUDService<Project, ProjectFullDto, ProjectUpdateDto> getService() {
        return projectService;
    }

    @Override
    @Mapping(source = "parentDirectory.id", target = "parentDirectoryId")
    @Mapping(source = "tasks", target = "taskIds")
    public abstract ProjectFullDto mapEntityToFullDto(Project project);

    @Override
    @Mapping(source = "id", target = "id")
    @Mapping(source = "taskIds", target = "tasks")
    @Mapping(source = "parentDirectoryId", target = "parentDirectory", qualifiedByName = "mapIdToEntity")
    public abstract Project mapFullDtoToEntity(ProjectFullDto projectFullDto);

    @Override
    @Mapping(source = "parentDirectory.id", target = "parentDirectoryId")
    public abstract ProjectUpdateDto mapEntityToUpdateDto(Project project);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(source = "parentDirectoryId", target = "parentDirectory", qualifiedByName = "mapIdToEntity")
    public abstract Project mapForSaveEntity(ProjectUpdateDto projectUpdateDto);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(source = "parentDirectoryId", target = "parentDirectory", qualifiedByName = "mapIdToEntity")
    public abstract Project mapForUpdateEntity(@MappingTarget Project project, ProjectUpdateDto projectUpdateDto);

}
