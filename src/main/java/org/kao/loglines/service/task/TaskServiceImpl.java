package org.kao.loglines.service.task;

import lombok.RequiredArgsConstructor;
import org.kao.loglines.entity.task.Task;
import org.kao.loglines.exception.GenericServiceException;
import org.kao.loglines.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

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
    public Task create(Task task) {
        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public Task update(Long id, Task task) {

        Task taskDb = get(id);
        if (Objects.nonNull(taskDb)) {
            task.setId(id);
        }
        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        taskRepository.delete(get(id));
    }
}

