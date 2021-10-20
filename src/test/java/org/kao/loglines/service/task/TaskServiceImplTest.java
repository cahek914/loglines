package org.kao.loglines.service.task;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kao.loglines.data.TestDataProvider;
import org.kao.loglines.configuration.SizeType;
import org.kao.loglines.entity.Task;
import org.kao.loglines.exception.GenericServiceException;
import org.kao.loglines.service.task.TaskService;
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

        taskList.forEach(task -> log.debug(taskService.create(task).toString()));

        List<Task> taskListDb = taskService.getList();
        assertThat(taskList.size()).isEqualTo(taskListDb.size());

    }

    @Test
    public void taskLastUpdatedTimeShouldBeAfterThanPreviousUpdate() {

        Task task = taskService.create(dataProvider.task(0));
        assertThat(task.getId()).isNotNull();

        String newTitle = "some new title";
        task.setTitle(newTitle);
        Task taskDb = taskService.update(task.getId(), task);

        assertThat(taskDb).isNotEqualTo(task);
        assertThat(taskDb.getTitle()).isEqualTo(newTitle);
        assertThat(taskDb.getUpdatedDate()).isAfter(task.getUpdatedDate());
        assertThat(taskDb.getCreatedDate()).isEqualTo(task.getCreatedDate());

    }

    @Test
    public void shouldThrownByValidationExceptionIfTaskTitleOrDescriptionIncreaseMaxSize() {

        Task task = dataProvider.task(1);
        assertThatThrownBy(() -> taskService.create(task)).isInstanceOf(ValidationException.class);

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