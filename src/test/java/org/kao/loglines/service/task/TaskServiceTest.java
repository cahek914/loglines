package org.kao.loglines.service.task;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.kao.loglines.data.DatabaseCleaner;
import org.kao.loglines.data.TestDataProvider;
import org.kao.loglines.dto.project.ProjectFullDto;
import org.kao.loglines.dto.task.TaskFullDto;
import org.kao.loglines.dto.task.TaskUpdateDto;
import org.kao.loglines.entity.project.Project;
import org.kao.loglines.entity.task.Task;
import org.kao.loglines.mapper.GenericMapper;
import org.kao.loglines.mapper.project.ProjectMapper;
import org.kao.loglines.mapper.task.TaskMapper;
import org.kao.loglines.service.GenericCRUDService;
import org.kao.loglines.service.GenericCRUDServiceTest;
import org.kao.loglines.service.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TaskServiceTest extends GenericCRUDServiceTest<Task, TaskFullDto, TaskUpdateDto> {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TestDataProvider dataProvider;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @AfterEach
    void tearDown() {
        databaseCleaner.clean();
    }

    @Override
    protected Task getEntity() {

        // create entity
        Task task = dataProvider.task(0);
        Project project = dataProvider.project(0);
        task.setProject(project);
        // store depended entity to database
        ProjectFullDto projectFullDto = projectService.create(
                projectMapper.mapEntityToUpdateDto(project));
        // apply actual ids
        task.getProject().setId(projectFullDto.getId());

        return task;
    }

    @Override
    protected Task getEntity(int maxSizeCorrector) {

        Task task = this.getEntity();
        task = dataProvider.setTitleAndDescription(task, maxSizeCorrector);

        return task;
    }

    @Override
    protected GenericMapper<Task, TaskFullDto, TaskUpdateDto> getMapper() {
        return taskMapper;
    }

    @Override
    protected GenericCRUDService<Task, TaskFullDto, TaskUpdateDto> getService() {
        return taskService;
    }

    @Test
    public void taskLastUpdatedTimeShouldBeAfterThanPreviousUpdate() throws InterruptedException {

        // create entity
        Task task = this.getEntity();
        // store entity to database
        TaskFullDto taskFullDto = taskService.create(taskMapper.mapEntityToUpdateDto(task));
        assertThat(taskFullDto.getId()).isNotNull();

        // create updateDto from entity
        TaskUpdateDto taskUpdateDto = taskMapper.mapEntityToUpdateDto(task);
        assertThat(taskUpdateDto).isNotNull();

        // update field for updateDto
        String newTitle = "some new title";
        taskUpdateDto.setTitle(newTitle);

        // store updated data to database
        Thread.sleep(500);
        TaskFullDto taskFullDtoDb = taskService.update(taskFullDto.getId(), taskUpdateDto);

        assertThat(taskFullDtoDb).isNotEqualTo(taskFullDto);
        assertThat(taskFullDtoDb.getProjectId()).isEqualTo(taskFullDto.getProjectId());
        assertThat(taskFullDtoDb.getCreatedDate()).isEqualToIgnoringNanos(taskFullDto.getCreatedDate());

        assertThat(taskFullDtoDb.getUpdatedDate()).isAfter(taskFullDto.getUpdatedDate());
        assertThat(taskFullDtoDb.getTitle()).isEqualTo(newTitle);

    }

}
