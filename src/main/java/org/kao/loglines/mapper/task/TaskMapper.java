package org.kao.loglines.mapper.task;

import org.kao.loglines.dto.task.TaskFullDto;
import org.kao.loglines.entity.task.Task;
import org.kao.loglines.mapper.GenericMapperImpl;
import org.kao.loglines.mapper.project.ProjectMapper;
import org.kao.loglines.service.GenericCRUDService;
import org.kao.loglines.service.project.ProjectService;
import org.kao.loglines.service.task.TaskService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {ProjectMapper.class, ProjectService.class})
public abstract class TaskMapper extends GenericMapperImpl<Task, TaskFullDto> {

    @Autowired
    private TaskService taskService;

    @Override
    public GenericCRUDService<Task, TaskFullDto> getService() {
        return taskService;
    }

    @Override
    @Mapping(source = "project.id", target = "projectId")
    public abstract TaskFullDto mapToDto(Task entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(source = "projectId", target = "project", qualifiedByName = "mapIdToEntity")
    public abstract Task mapToEntity(TaskFullDto updateDto);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(source = "projectId", target = "project", qualifiedByName = "mapIdToEntity")
    public abstract Task mapUpdateEntity(@MappingTarget Task task, TaskFullDto updateDto);

}
