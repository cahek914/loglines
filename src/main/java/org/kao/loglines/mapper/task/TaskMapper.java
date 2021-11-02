package org.kao.loglines.mapper.task;

import org.kao.loglines.dto.task.TaskFullDto;
import org.kao.loglines.dto.task.TaskUpdateDto;
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
public abstract class TaskMapper extends GenericMapperImpl<Task, TaskFullDto, TaskUpdateDto> {

    @Autowired
    private TaskService taskService;

    @Override
    protected GenericCRUDService<Task, TaskFullDto, TaskUpdateDto> getService() {
        return taskService;
    }

    @Override
    @Mapping(source = "id", target = "id")
    @Mapping(source = "project.id", target = "projectId")
    public abstract TaskFullDto mapEntityToFullDto(Task entity);

    @Override
    @Mapping(source = "id", target = "id")
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(source = "projectId", target = "project", qualifiedByName = "mapIdToEntity")
    public abstract Task mapFullDtoToEntity(TaskFullDto taskFullDto);

    @Override
    @Mapping(source = "project.id", target = "projectId")
    public abstract TaskUpdateDto mapEntityToUpdateDto(Task task);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(source = "projectId", target = "project", qualifiedByName = "mapIdToEntity")
    public abstract Task mapForSaveEntity(TaskUpdateDto taskUpdateDto);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(source = "projectId", target = "project", qualifiedByName = "mapIdToEntity")
    public abstract Task mapForUpdateEntity(@MappingTarget Task task, TaskUpdateDto taskUpdateDto);

}
