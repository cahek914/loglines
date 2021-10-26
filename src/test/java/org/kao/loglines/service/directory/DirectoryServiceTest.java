package org.kao.loglines.service.directory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kao.loglines.data.DatabaseCleaner;
import org.kao.loglines.data.TestDataProvider;
import org.kao.loglines.entity.directory.Directory;
import org.kao.loglines.entity.project.Project;
import org.kao.loglines.mapper.directory.DirectoryMapper;
import org.kao.loglines.mapper.project.ProjectMapper;
import org.kao.loglines.mapper.task.TaskMapper;
import org.kao.loglines.service.project.ProjectService;
import org.kao.loglines.service.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class DirectoryServiceTest {

    @Autowired
    private TestDataProvider dataProvider;

    @Autowired
    private DirectoryService directoryService;

    @Autowired
    private DirectoryMapper directoryMapper;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
//        databaseCleaner.clean();
    }

    @Test
    public void mapperShouldRetrieveOnlyProjectForCurrentDirectory() {

        Directory parentDirectory = dataProvider.directory(0);
        parentDirectory = directoryService.create(directoryMapper.mapToDto(parentDirectory));

        assertThat(parentDirectory).isNotNull();
        assertThat(parentDirectory.getId()).isNotNull();

        Directory directory = dataProvider.directory(0);
        directory.setParentDirectory(parentDirectory);
        directory = directoryService.create(directoryMapper.mapToDto(directory));

        assertThat(directory).isNotNull();
        assertThat(directory.getId()).isNotNull();

        Project projectOne = dataProvider.project(0);
        projectOne.setParentDirectory(parentDirectory);
        projectOne = projectService.create(projectMapper.mapToDto(projectOne));

        assertThat(projectOne).isNotNull();
        assertThat(projectOne.getId()).isNotNull();

        Project projectTwo = dataProvider.project(0);
        projectTwo.setParentDirectory(directory);
        projectTwo = projectService.create(projectMapper.mapToDto(projectTwo));

        assertThat(projectTwo).isNotNull();
        assertThat(projectTwo.getId()).isNotNull();

        Project projectThree = dataProvider.project(0);
        projectThree = projectService.create(projectMapper.mapToDto(projectThree));

        assertThat(projectThree).isNotNull();
        assertThat(projectThree.getId()).isNotNull();


        parentDirectory = directoryService.get(parentDirectory.getId());
        List<Project> projects = parentDirectory.getProjects();

        assertThat(projects).isNotNull();

        parentDirectory = directoryMapper.mapToEntity(directoryMapper.mapToDto(parentDirectory));

        assertThat(parentDirectory.getProjects()).hasSize(1);


    }

//    @Test
//    public void createdDirectoryShouldEqualToRetrieved() {
//
//        Directory directory = dataProvider.directory(0);
//
//        directoryService.create()
//
//        List<Task> taskListDb = taskService.getList();
//        assertThat(taskList.size()).isEqualTo(taskListDb.size());
//
//    }
//
//    @Test
//    public void taskLastUpdatedTimeShouldBeAfterThanPreviousUpdate() {
//
//        Task task = taskService.create(taskMapper.mapToDto(dataProvider.task(0)));
//        TaskUpdateDto updateDto = taskMapper.mapToDto(task);
//        assertThat(task.getId()).isNotNull();
//        assertThat(updateDto).isNotNull();
//
//        String newTitle = "some new title";
//        updateDto.setTitle(newTitle);
//        Task taskDb = taskService.update(task.getId(), updateDto);
//
//        assertThat(taskDb).isNotEqualTo(task);
//        assertThat(taskDb.getTitle()).isEqualTo(newTitle);
//        assertThat(taskDb.getUpdatedDate()).isAfter(task.getUpdatedDate());
//        assertThat(taskDb.getCreatedDate()).isEqualToIgnoringNanos(task.getCreatedDate());
//
//    }
//
//    @Test
//    public void shouldThrownByValidationExceptionIfTaskTitleOrDescriptionIncreaseMaxSize() {
//
//        Task task = dataProvider.task(1);
//        assertThatThrownBy(() -> taskService.create(taskMapper.mapToDto(task))).isInstanceOf(ValidationException.class);
//
//    }
//
//    @Test
//    public void shouldThrownExceptionIfTaskToGetByIdNotExist() {
//
//        assertThatThrownBy(() -> taskService.get(10L)).isInstanceOf(GenericServiceException.NotFound.class);
//
//    }
//
//    @Test
//    public void shouldThrownExceptionIfTaskToDeleteByIdNotExist() {
//
//        assertThatThrownBy(() -> taskService.deleteById(10L)).isInstanceOf(GenericServiceException.NotFound.class);
//
//    }

}