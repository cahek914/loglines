package org.kao.loglines.mapper.project;

import org.junit.jupiter.api.Test;
import org.kao.loglines.data.TestDataProvider;
import org.kao.loglines.dto.project.ProjectFullDto;
import org.kao.loglines.dto.project.ProjectUpdateDto;
import org.kao.loglines.entity.directory.Directory;
import org.kao.loglines.entity.project.Project;
import org.kao.loglines.mapper.GenericMapper;
import org.kao.loglines.mapper.GenericMapperImplTest;
import org.kao.loglines.service.GenericCRUDService;
import org.kao.loglines.service.directory.DirectoryService;
import org.kao.loglines.service.project.ProjectService;
import org.kao.loglines.service.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProjectMapperTest extends GenericMapperImplTest<Project, ProjectFullDto, ProjectUpdateDto> {

    @Autowired
    private TestDataProvider dataProvider;

    @Autowired
    private ProjectMapper projectMapper;

    @MockBean
    private DirectoryService directoryService;

    @MockBean
    private ProjectService projectService;

    @MockBean
    private TaskService taskService;

    @Override
    protected Project getEntity() {
        return dataProvider.project();
    }

    @Override
    protected GenericMapper<Project, ProjectFullDto, ProjectUpdateDto> getMapper() {
        return projectMapper;
    }

    @Override
    protected GenericCRUDService<Project, ProjectFullDto, ProjectUpdateDto> getMockedService() {
        return projectService;
    }

    @Override
    @Test
    public void mapEntityToFullDto() {

        Project project = dataProvider.projectWithParentDirectoryAndTasks();
        ProjectFullDto projectFullDto = projectMapper.mapEntityToFullDto(project);

        assertThat(projectFullDto.getId()).isEqualTo(project.getId());
        assertThat(projectFullDto.getParentDirectoryId()).isEqualTo(project.getParentDirectory().getId());
        assertThat(projectFullDto.getTaskIds().size()).isEqualTo(project.getTasks().size());
        assertThat(projectFullDto.getStartDate()).isEqualToIgnoringNanos(project.getStartDate());
        assertThat(projectFullDto.getEndDate()).isEqualToIgnoringNanos(project.getEndDate());
        assertThat(projectFullDto.getTitle()).isEqualTo(project.getTitle());
        assertThat(projectFullDto.getDescription()).isEqualTo(project.getDescription());

    }

    @Override
    @Test
    public void mapFullDtoToEntity() {

        Project project = dataProvider.projectWithParentDirectoryAndTasks();
        ProjectFullDto projectFullDto = projectMapper.mapEntityToFullDto(project);

        when(directoryService.getEntity(anyLong())).thenReturn(project.getParentDirectory());
        when(taskService.getList(anyCollection())).thenReturn(project.getTasks());

        Project mappedProject = projectMapper.mapFullDtoToEntity(projectFullDto);

        assertThat(mappedProject.getId()).isEqualTo(projectFullDto.getId());
        assertThat(mappedProject.getParentDirectory().getId()).isEqualTo(projectFullDto.getParentDirectoryId());
        assertThat(mappedProject.getTasks().size()).isEqualTo(projectFullDto.getTaskIds().size());
        assertThat(mappedProject.getStartDate()).isEqualToIgnoringNanos(projectFullDto.getStartDate());
        assertThat(mappedProject.getEndDate()).isEqualToIgnoringNanos(projectFullDto.getEndDate());
        assertThat(mappedProject.getTitle()).isEqualTo(projectFullDto.getTitle());
        assertThat(mappedProject.getDescription()).isEqualTo(projectFullDto.getDescription());

    }

    @Override
    @Test
    public void mapEntityToUpdateDto() {

        Project project = dataProvider.projectWithParentDirectoryAndTasks();

        ProjectUpdateDto updateDto = projectMapper.mapEntityToUpdateDto(project);

        assertThat(updateDto.getStartDate()).isEqualToIgnoringNanos(project.getStartDate());
        assertThat(updateDto.getEndDate()).isEqualToIgnoringNanos(project.getEndDate());
        assertThat(updateDto.getParentDirectoryId()).isEqualTo(project.getParentDirectory().getId());
        assertThat(updateDto.getTitle()).isEqualTo(project.getTitle());
        assertThat(updateDto.getDescription()).isEqualTo(project.getDescription());

    }

    @Override
    @Test
    public void mapForSaveEntity() {

        ProjectUpdateDto updateDto = dataProvider.projectUpdateDto();
        Directory parentDirectory = dataProvider.directory();
        updateDto.setParentDirectoryId(parentDirectory.getId());

        when(directoryService.getEntity(parentDirectory.getId())).thenReturn(parentDirectory);

        Project mappedProject = projectMapper.mapForSaveEntity(updateDto);

        assertThat(mappedProject.getId()).isNull();
        assertThat(mappedProject.getParentDirectory().getId()).isEqualTo(updateDto.getParentDirectoryId());
        assertThat(mappedProject.getStartDate()).isEqualToIgnoringNanos(updateDto.getStartDate());
        assertThat(mappedProject.getEndDate()).isEqualToIgnoringNanos(updateDto.getEndDate());
        assertThat(mappedProject.getTitle()).isEqualTo(updateDto.getTitle());
        assertThat(mappedProject.getDescription()).isEqualTo(updateDto.getDescription());
    }

    @Override
    @Test
    public void mapForUpdateEntity() {

        Project project = dataProvider.projectWithParentDirectoryAndTasks();
        ProjectUpdateDto updateDto = projectMapper.mapEntityToUpdateDto(project);
        updateDto.setTitle("new title");

        when(directoryService.getEntity(anyLong())).thenReturn(project.getParentDirectory());

        Project mappedProject = projectMapper.mapForUpdateEntity(project, updateDto);

        assertThat(mappedProject.getId()).isEqualTo(project.getId());

        assertThat(mappedProject.getParentDirectory().getId()).isEqualTo(updateDto.getParentDirectoryId());
        assertThat(mappedProject.getStartDate()).isEqualToIgnoringNanos(updateDto.getStartDate());
        assertThat(mappedProject.getEndDate()).isEqualToIgnoringNanos(updateDto.getEndDate());
        assertThat(mappedProject.getTitle()).isEqualTo(updateDto.getTitle());
        assertThat(mappedProject.getDescription()).isEqualTo(updateDto.getDescription());

    }
}
