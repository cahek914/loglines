package org.kao.loglines.data;

import org.kao.loglines.dto.directory.DirectoryFullDto;
import org.kao.loglines.dto.project.ProjectFullDto;
import org.kao.loglines.dto.task.TaskFullDto;
import org.kao.loglines.service.directory.DirectoryService;
import org.kao.loglines.service.project.ProjectService;
import org.kao.loglines.service.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseCleaner {

    @Autowired
    private DirectoryService directoryService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskService;

    public void clean() {

        cleanDirectories();

        List<ProjectFullDto> projects = projectService.getList();
        projects.forEach(project -> projectService.deleteById(project.getId()));

        List<TaskFullDto> tasks = taskService.getList();
        tasks.forEach(task -> taskService.deleteById(task.getId()));

    }

    private void cleanDirectories() {
        List<DirectoryFullDto> directories = directoryService.getList();
        if (directories.listIterator().hasNext()) {
            directoryService.deleteById(directories.listIterator().next().getId());
            cleanDirectories();
        }
    }

}
