package org.kao.loglines.mapper.directory;

import org.junit.jupiter.api.Test;
import org.kao.loglines.data.TestDataProvider;
import org.kao.loglines.dto.directory.DirectoryFullDto;
import org.kao.loglines.entity.directory.Directory;
import org.kao.loglines.entity.project.Project;
import org.kao.loglines.service.directory.DirectoryService;
import org.kao.loglines.service.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DirectoryMapperTest {

    @Autowired
    private TestDataProvider dataProvider;

    @Autowired
    private DirectoryMapper mapper;

    @MockBean
    private DirectoryService directoryService;

    @MockBean
    private ProjectService projectService;

    @Test
    public void mappedIdsSizeShouldEqualsToEntitiesSize() {

        List<Directory> directories = dataProvider.getRandomListOf(dataProvider::directory, 0, 2, 10);
        List<Long> ids = mapper.mapEntitiesToIds(directories);

        assertThat(directories.size()).isEqualTo(ids.size());

    }

    @Test
    public void mappingToUpdateDto() {

        Directory directory = getDirectoryWithProjectAndParent();
        DirectoryFullDto updateDto = mapper.mapToDto(directory);

        assertThat(directory.getProjects().size()).isEqualTo(updateDto.getProjectIds().size());
        assertThat(directory.getParentDirectory().getId()).isEqualTo(updateDto.getParentDirectoryId());
        assertThat(directory.getTitle()).isEqualTo(updateDto.getTitle());
        assertThat(directory.getDescription()).isEqualTo(updateDto.getDescription());

    }

    @Test
    public void mappingToEntity() {


        Directory directory = getDirectoryWithProjectAndParent();
        assertThat(directory.getProjects()).hasSize(1);
        Project project = directory.getProjects().get(0);

        when(directoryService.get(anyLong())).thenReturn(directory.getParentDirectory());
        when(projectService.get(anyLong())).thenReturn(project);

        DirectoryFullDto updateDto = mapper.mapToDto(directory);

        Directory mappedDirectory = mapper.mapToEntity(updateDto);
        mappedDirectory.setId(directory.getId());

        assertThat(directory).isEqualTo(mappedDirectory);

    }

    @Test
    public void mappingUpdateEntity() {

        Directory directory = getDirectoryWithProjectAndParent();
        assertThat(directory.getProjects()).hasSize(1);
        Project project = directory.getProjects().get(0);

        when(directoryService.get(anyLong())).thenReturn(directory.getParentDirectory());
        when(projectService.get(anyLong())).thenReturn(project);

        DirectoryFullDto updateDto = mapper.mapToDto(directory);
        updateDto.setTitle("new title");
        Directory mappedDirectory = mapper.mapUpdateEntity(directory, updateDto);

        assertThat(mappedDirectory.getId()).isEqualTo(directory.getId());
        assertThat(mappedDirectory.getProjects().size()).isEqualTo(directory.getProjects().size());
        assertThat(mappedDirectory.getParentDirectory()).isEqualTo(directory.getParentDirectory());
        assertThat(mappedDirectory.getDescription()).isEqualTo(directory.getDescription());

        assertThat(mappedDirectory.getTitle()).isEqualTo(updateDto.getTitle());

    }


    private Directory getDirectoryWithProjectAndParent() {

        Directory directory = dataProvider.directory();
        Project project = dataProvider.project();
        project.setParentDirectory(directory);
        directory.setProjects(new ArrayList<>(Collections.singletonList(project)));
        directory.setParentDirectory(dataProvider.directory());

        return directory;
    }

}