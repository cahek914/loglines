package org.kao.loglines.controller.task;

import lombok.RequiredArgsConstructor;
import org.kao.loglines.controller.GenericCRUDController;
import org.kao.loglines.entity.Task;
import org.kao.loglines.service.GenericService;
import org.kao.loglines.service.task.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController extends GenericCRUDController<Task> {

    private final TaskService taskService;

    @Override
    public GenericService<Task> getService() {
        return taskService;
    }

}
