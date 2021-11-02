package org.kao.loglines.service.task;

import org.kao.loglines.dto.task.TaskFullDto;
import org.kao.loglines.dto.task.TaskUpdateDto;
import org.kao.loglines.entity.task.Task;
import org.kao.loglines.service.GenericCRUDService;

public interface TaskService extends GenericCRUDService<Task, TaskFullDto, TaskUpdateDto> {
}
