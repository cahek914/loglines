package org.kao.loglines.data;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.kao.loglines.configuration.SizeType;
import org.kao.loglines.model.directory.DirectoryUpdateDto;
import org.kao.loglines.model.project.ProjectUpdateDto;
import org.kao.loglines.model.task.TaskUpdateDto;
import org.kao.loglines.entity.TitleDescription;
import org.kao.loglines.entity.directory.Directory;
import org.kao.loglines.entity.project.Project;
import org.kao.loglines.entity.task.Task;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@Component
public class TestDataProvider {

    public <T> List<T> getRandomListOf(Function<Integer, T> createEntity, Integer maxSizeCorrector, Integer minListSize, Integer listSizeDelta) {

        List<T> list = new ArrayList<>();
        for(int i = 0; i < (int) (Math.random() * listSizeDelta) + minListSize; i++) {
            list.add(createEntity.apply(maxSizeCorrector));
        }
        return list;
    }

    public Directory directory(int maxSizeCorrector) {
        Directory directory = new Directory();
        return setTitleAndDescription(directory, maxSizeCorrector);
    }

    public Directory directory() {
        Directory directory = new Directory();
        directory.setId(IdGenerator.getId());
        return setTitleAndDescription(directory, 0);
    }

    public DirectoryUpdateDto directoryUpdateDto() {
        return setTitleAndDescription(new DirectoryUpdateDto(), 0);
    }

    public Directory directoryWithProjectAndParent() {

        Directory directory = directory();
        Project projectOne = project();
        projectOne.setParentDirectory(directory);
        Project projectTwo = project();
        projectTwo.setParentDirectory(directory);
        List<Project> projects = new ArrayList<>();
        projects.add(projectOne);
        projects.add(projectTwo);
        directory.setProjects(projects);
        directory.setParentDirectory(directory());

        return directory;
    }

    public Project project(int maxSizeCorrector) {
        Project project = new Project();
        project.setStartDate(LocalDateTime.now());
        project.setEndDate(LocalDateTime.now().plusHours(5));
        return setTitleAndDescription(project, maxSizeCorrector);
    }

    public Project project() {
        Project project = new Project();
        project.setId(IdGenerator.getId());
        project.setStartDate(LocalDateTime.now());
        project.setEndDate(LocalDateTime.now().plusHours(5));
        return setTitleAndDescription(project, 0);
    }

    public ProjectUpdateDto projectUpdateDto() {
        ProjectUpdateDto projectUpdateDto = new ProjectUpdateDto();
        projectUpdateDto.setStartDate(LocalDateTime.now());
        projectUpdateDto.setEndDate(LocalDateTime.now().plusHours(5));
        return setTitleAndDescription(projectUpdateDto, 0);
    }

    public Project projectWithParentDirectoryAndTasks() {

        Project project = project();
        Directory directory = directory();
        project.setParentDirectory(directory);
        directory.setProjects(new ArrayList<>(Collections.singletonList(project)));

        Task taskOne = task();
        taskOne.setProject(project);
        Task taskTwo = task();
        taskTwo.setProject(project);
        List<Task> tasks = new ArrayList<>();
        tasks.add(taskOne);
        tasks.add(taskTwo);
        project.setTasks(tasks);

        return project;
    }

    public Task task(int maxSizeCorrector) {
        return setTitleAndDescription(new Task(), maxSizeCorrector);
    }

    public Task task() {
        Task task = new Task();
        task.setId(IdGenerator.getId());
        return setTitleAndDescription(task, 0);
    }

    public TaskUpdateDto taskUpdateDto() {
        return setTitleAndDescription(new TaskUpdateDto(), 0);
    }

    public Task taskWithProject() {
        Project project = project();
        Task task = task();
        project.setTasks(Collections.singletonList(task));
        task.setProject(project);
        return task;
    }

    public Task taskDateWrapper(Task task) {
        task.setCreatedDate(LocalDateTime.now());
        task.setUpdatedDate(LocalDateTime.now());
        return task;
    }

    public <T extends TitleDescription> T setTitleAndDescription(T entity, int maxSizeCorrector) {
        entity.setTitle(RandomString.make(SizeType.TITLE_MAX_SIZE + maxSizeCorrector));
        entity.setDescription(RandomString.make(SizeType.DESCRIPTION_MAX_SIZE + maxSizeCorrector));
        return entity;
    }

    private static class IdGenerator {

        private static Long id = 1L;

        public static Long getId() {
            return id++;
        }

    }

}
