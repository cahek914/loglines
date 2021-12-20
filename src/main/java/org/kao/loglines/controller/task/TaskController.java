package org.kao.loglines.controller.task;

import lombok.RequiredArgsConstructor;
import org.kao.loglines.controller.GenericCRUDController;
import org.kao.loglines.model.task.TaskFullDto;
import org.kao.loglines.model.task.TaskUpdateDto;
import org.kao.loglines.entity.task.Task;
import org.kao.loglines.service.GenericCRUDService;
import org.kao.loglines.service.task.TaskService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController extends GenericCRUDController<Task, TaskFullDto, TaskUpdateDto> {

    private final TaskService taskService;

    @Override
    public GenericCRUDService<Task, TaskFullDto, TaskUpdateDto> getService() {
        return taskService;
    }

}
