package org.kao.loglines.data;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.kao.loglines.configuration.SizeType;
import org.kao.loglines.entity.TitleDescription;
import org.kao.loglines.entity.directory.Directory;
import org.kao.loglines.entity.project.Project;
import org.kao.loglines.entity.task.Task;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public Task task(int maxSizeCorrector) {
        Task task = new Task();
        return setTitleAndDescription(task, maxSizeCorrector);
    }

    public Task task(Long id) {
        Task task = new Task();
        task.setId(id);
        task.setCreatedDate(LocalDateTime.now());
        task.setUpdatedDate(LocalDateTime.now());
        return setTitleAndDescription(task, 0);
    }

//    public Task taskDateWrapper(Task task) {
//        task.setCreatedDate(LocalDateTime.now());
//        task.setUpdatedDate(LocalDateTime.now());
//        return task;
//    }

//    public Task taskFull(int maxSizeCorrector) {
//        Task task = new Task();
//        task.setId(1L);
//        task.setCreatedDate(LocalDateTime.now());
//        task.setUpdatedDate(LocalDateTime.now());
//        return setTitleAndDescription(task, maxSizeCorrector);
//    }

    private <T extends TitleDescription> T setTitleAndDescription(T entity, int maxSizeCorrector) {
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
