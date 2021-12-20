package org.kao.loglines.mapper.task;

import org.junit.jupiter.api.Test;
import org.kao.loglines.data.TestDataProvider;
import org.kao.loglines.model.task.TaskFullDto;
import org.kao.loglines.model.task.TaskUpdateDto;
import org.kao.loglines.entity.project.Project;
import org.kao.loglines.entity.task.Task;
import org.kao.loglines.mapper.GenericMapper;
import org.kao.loglines.mapper.GenericMapperImplTest;
import org.kao.loglines.service.GenericCRUDService;
import org.kao.loglines.service.project.ProjectService;
import org.kao.loglines.service.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class TaskMapperTest extends GenericMapperImplTest<Task, TaskFullDto, TaskUpdateDto> {

    @Autowired
    private TestDataProvider dataProvider;

    @Autowired
    private TaskMapper taskMapper;

    @MockBean
    private TaskService taskService;

    @MockBean
    private ProjectService projectService;

    @Override
    protected Task getEntity() {
        return dataProvider.task();
    }

    @Override
    protected GenericMapper<Task, TaskFullDto, TaskUpdateDto> getMapper() {
        return taskMapper;
    }

    @Override
    protected GenericCRUDService<Task, TaskFullDto, TaskUpdateDto> getMockedService() {
        return taskService;
    }

    @Override
    @Test
    public void mapEntityToFullDto() {

        Task task = dataProvider.taskDateWrapper(dataProvider.taskWithProject());
        TaskFullDto taskFullDto = taskMapper.mapEntityToFullDto(task);

        assertThat(taskFullDto.getId()).isEqualTo(task.getId());
        assertThat(taskFullDto.getCreatedDate()).isEqualToIgnoringNanos(task.getCreatedDate());
        assertThat(taskFullDto.getUpdatedDate()).isEqualToIgnoringNanos(task.getUpdatedDate());
        assertThat(taskFullDto.getProjectId()).isEqualTo(task.getProject().getId());
        assertThat(taskFullDto.getTitle()).isEqualTo(task.getTitle());
        assertThat(taskFullDto.getDescription()).isEqualTo(task.getDescription());

    }

    @Override
    @Test
    public void mapEntityToUpdateDto() {

        Task task = dataProvider.taskWithProject();
        TaskUpdateDto taskUpdateDto = taskMapper.mapEntityToUpdateDto(task);

        assertThat(taskUpdateDto.getProjectId()).isEqualTo(task.getProject().getId());
        assertThat(taskUpdateDto.getTitle()).isEqualTo(task.getTitle());
        assertThat(taskUpdateDto.getDescription()).isEqualTo(task.getDescription());

    }

    @Override
    @Test
    public void mapFullDtoToEntity() {

        Task task = dataProvider.taskWithProject();
        TaskFullDto taskFullDto = taskMapper.mapEntityToFullDto(task);

        when(projectService.getEntity(task.getProject().getId())).thenReturn(task.getProject());

        Task mappedTask = taskMapper.mapFullDtoToEntity(taskFullDto);

        assertThat(mappedTask.getId()).isEqualTo(taskFullDto.getId());
        assertThat(mappedTask.getProject().getId()).isEqualTo(taskFullDto.getProjectId());
        assertThat(mappedTask.getTitle()).isEqualTo(taskFullDto.getTitle());
        assertThat(mappedTask.getDescription()).isEqualTo(taskFullDto.getDescription());

    }

    @Override
    @Test
    public void mapForSaveEntity() {

        TaskUpdateDto taskUpdateDto = dataProvider.taskUpdateDto();
        Project project = dataProvider.project();
        taskUpdateDto.setProjectId(project.getId());

        when(projectService.getEntity(project.getId())).thenReturn(project);

        Task mappedTask = taskMapper.mapForSaveEntity(taskUpdateDto);

        assertThat(mappedTask.getId()).isNull();
        assertThat(mappedTask.getProject().getId()).isEqualTo(taskUpdateDto.getProjectId());
        assertThat(mappedTask.getTitle()).isEqualTo(taskUpdateDto.getTitle());
        assertThat(mappedTask.getDescription()).isEqualTo(taskUpdateDto.getDescription());

    }

    @Override
    @Test
    public void mapForUpdateEntity() {

        Task task = dataProvider.taskWithProject();
        TaskUpdateDto taskUpdateDto = taskMapper.mapEntityToUpdateDto(task);
        taskUpdateDto.setTitle("new title");

        when(projectService.getEntity(task.getProject().getId())).thenReturn(task.getProject());

        Task mappedTask = taskMapper.mapForUpdateEntity(task, taskUpdateDto);

        assertThat(mappedTask.getId()).isEqualTo(task.getId());

        assertThat(mappedTask.getProject().getId()).isEqualTo(taskUpdateDto.getProjectId());
        assertThat(mappedTask.getTitle()).isEqualTo(taskUpdateDto.getTitle());
        assertThat(mappedTask.getDescription()).isEqualTo(taskUpdateDto.getDescription());

    }
}
