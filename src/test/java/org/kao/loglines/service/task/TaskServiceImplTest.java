package org.kao.loglines.service.task;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kao.loglines.data.TestDataProvider;
import org.kao.loglines.dto.task.TaskFullDto;
import org.kao.loglines.entity.task.Task;
import org.kao.loglines.exception.GenericServiceException;
import org.kao.loglines.mapper.task.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ValidationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

@Slf4j
@SpringBootTest
class TaskServiceImplTest {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TestDataProvider dataProvider;

    @Autowired
    private TaskMapper taskMapper;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        List<Task> taskListDb = taskService.getList();
        if (!taskListDb.isEmpty()) {
            taskListDb.forEach(task -> taskService.deleteById(task.getId()));
        }
        taskListDb = taskService.getList();
        assertThat(taskListDb).isEmpty();
    }

    @Test
    public void createdTaskListSizeShouldEqualToRetrieved() {

        List<Task> taskList = dataProvider.getRandomListOf(dataProvider::task, 0, 2, 10);

        taskList.forEach(task -> log.info(taskService.create(taskMapper.mapEntityToFullDto(task)).toString()));

        List<Task> taskListDb = taskService.getList();
        assertThat(taskList.size()).isEqualTo(taskListDb.size());

    }

    @Test
    public void taskLastUpdatedTimeShouldBeAfterThanPreviousUpdate() {

        Task task = taskService.create(taskMapper.mapEntityToFullDto(dataProvider.task(0)));
        TaskFullDto updateDto = taskMapper.mapEntityToFullDto(task);
        assertThat(task.getId()).isNotNull();
        assertThat(updateDto).isNotNull();

        String newTitle = "some new title";
        updateDto.setTitle(newTitle);
        Task taskDb = taskService.update(task.getId(), updateDto);

        assertThat(taskDb).isNotEqualTo(task);
        assertThat(taskDb.getTitle()).isEqualTo(newTitle);
        assertThat(taskDb.getUpdatedDate()).isAfter(task.getUpdatedDate());
        assertThat(taskDb.getCreatedDate()).isEqualToIgnoringNanos(task.getCreatedDate());

    }

    @Test
    public void shouldThrownByValidationExceptionIfTaskTitleOrDescriptionIncreaseMaxSize() {

        Task task = dataProvider.task(1);
        assertThatThrownBy(() -> taskService.create(taskMapper.mapEntityToFullDto(task))).isInstanceOf(ValidationException.class);

    }

    @Test
    public void shouldThrownExceptionIfTaskToGetByIdNotExist() {

        assertThatThrownBy(() -> taskService.get(10L)).isInstanceOf(GenericServiceException.NotFound.class);

    }

    @Test
    public void shouldThrownExceptionIfTaskToDeleteByIdNotExist() {

        assertThatThrownBy(() -> taskService.deleteById(10L)).isInstanceOf(GenericServiceException.NotFound.class);

    }

}
