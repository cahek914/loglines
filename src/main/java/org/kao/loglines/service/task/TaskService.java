package org.kao.loglines.service.task;

import org.kao.loglines.model.task.TaskFullDto;
import org.kao.loglines.model.task.TaskUpdateDto;
import org.kao.loglines.entity.task.Task;
import org.kao.loglines.service.GenericCRUDService;

public interface TaskService extends GenericCRUDService<Task, TaskFullDto, TaskUpdateDto> {
}
