package org.kao.loglines.data;

import org.kao.loglines.mapper.directory.DirectoryMapper;
import org.kao.loglines.mapper.project.ProjectMapper;
import org.kao.loglines.mapper.task.TaskMapper;
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
    private DirectoryMapper directoryMapper;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskMapper taskMapper;

    public void clean() {

        List<Long> directoryIds = directoryMapper.mapEntitiesToIds(directoryService.getList());
        directoryIds.forEach(id -> directoryService.deleteById(id));

        List<Long> projectIds = projectMapper.mapEntitiesToIds(projectService.getList());
        projectIds.forEach(id -> projectService.deleteById(id));

        List<Long> taskIds = taskMapper.mapEntitiesToIds(taskService.getList());
        taskIds.forEach(id -> taskService.deleteById(id));

    }

}
