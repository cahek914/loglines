package org.kao.loglines.service.task;

import lombok.RequiredArgsConstructor;
import org.kao.loglines.dto.task.TaskUpdateDto;
import org.kao.loglines.entity.task.Task;
import org.kao.loglines.exception.GenericServiceException;
import org.kao.loglines.mapper.task.TaskMapper;
import org.kao.loglines.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Task get(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new GenericServiceException.NotFound(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getList() {
        return taskRepository.findAll();
    }

    @Override
    @Transactional
    public Task create(TaskUpdateDto updateDto) {
        return taskRepository.save(mapper.map(updateDto));
    }

    @Override
    @Transactional
    public Task update(Long id, TaskUpdateDto updateDto) {
        Task taskDb = get(id);
        taskDb.setTitle(updateDto.getTitle());
        taskDb.setDescription(updateDto.getDescription());
        return taskRepository.save(taskDb);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        taskRepository.delete(get(id));
    }
}

