package org.kao.loglines.controller.task;

import org.kao.loglines.controller.GenericCRUDControllerTest;
import org.kao.loglines.data.TestDataProvider;
import org.kao.loglines.dto.task.TaskFullDto;
import org.kao.loglines.dto.task.TaskUpdateDto;
import org.kao.loglines.entity.task.Task;
import org.kao.loglines.mapper.GenericMapper;
import org.kao.loglines.mapper.task.TaskMapper;
import org.kao.loglines.service.GenericCRUDService;
import org.kao.loglines.service.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class TaskControllerTest extends GenericCRUDControllerTest<Task, TaskFullDto, TaskUpdateDto> {

    private static final String URL_ROOT = "/tasks";
    private static final String URL_ID = "/tasks/{id}";

    @Autowired
    private TestDataProvider dataProvider;

    @Autowired
    private TaskMapper taskMapper;

    @MockBean
    private TaskService taskService;

    @Override
    protected Class<TaskFullDto> getDtoFullClass() {
        return TaskFullDto.class;
    }

    @Override
    protected Class<TaskUpdateDto> getDtoUpdateClass() {
        return TaskUpdateDto.class;
    }

    @Override
    protected String getRootUrl() {
        return URL_ROOT;
    }

    @Override
    protected String getUrlWithId() {
        return URL_ID;
    }

    @Override
    protected Task getEntity() {
        return dataProvider.task(0);
    }

    @Override
    protected Task getEntity(int maxSizeCorrector) {
        return dataProvider.task(maxSizeCorrector);
    }

    @Override
    protected GenericMapper<Task, TaskFullDto, TaskUpdateDto> getMapper() {
        return taskMapper;
    }

    @Override
    protected GenericCRUDService<Task, TaskFullDto, TaskUpdateDto> getService() {
        return taskService;
    }
}
