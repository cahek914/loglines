package org.kao.loglines.mapper.task;

import org.kao.loglines.dto.task.TaskUpdateDto;
import org.kao.loglines.entity.task.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    TaskUpdateDto map(Task entity);

}
