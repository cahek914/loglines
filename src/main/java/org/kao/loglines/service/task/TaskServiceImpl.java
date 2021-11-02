package org.kao.loglines.service.task;

import lombok.RequiredArgsConstructor;
import org.kao.loglines.dto.task.TaskFullDto;
import org.kao.loglines.dto.task.TaskUpdateDto;
import org.kao.loglines.entity.task.Task;
import org.kao.loglines.mapper.GenericMapper;
import org.kao.loglines.mapper.task.TaskMapper;
import org.kao.loglines.repository.TaskRepository;
import org.kao.loglines.service.GenericCRUDServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl extends GenericCRUDServiceImpl<Task, TaskFullDto, TaskUpdateDto> implements TaskService {

    private final TaskRepository repository;

    @Autowired
    private TaskMapper mapper;

    @Override
    public JpaRepository<Task, Long> getRepository() {
        return repository;
    }

    @Override
    public GenericMapper<Task, TaskFullDto, TaskUpdateDto> getMapper() {
        return mapper;
    }

}

