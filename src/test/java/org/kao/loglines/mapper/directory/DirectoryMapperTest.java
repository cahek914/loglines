package org.kao.loglines.mapper.directory;

import org.junit.jupiter.api.Test;
import org.kao.loglines.data.TestDataProvider;
import org.kao.loglines.model.directory.DirectoryFullDto;
import org.kao.loglines.model.directory.DirectoryUpdateDto;
import org.kao.loglines.entity.directory.Directory;
import org.kao.loglines.mapper.GenericMapper;
import org.kao.loglines.mapper.GenericMapperImplTest;
import org.kao.loglines.service.GenericCRUDService;
import org.kao.loglines.service.directory.DirectoryService;
import org.kao.loglines.service.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DirectoryMapperTest extends GenericMapperImplTest<Directory, DirectoryFullDto, DirectoryUpdateDto> {

    @Autowired
    private TestDataProvider dataProvider;

    @Autowired
    private DirectoryMapper directoryMapper;

    @MockBean
    private DirectoryService directoryService;

    @MockBean
    private ProjectService projectService;

    @Override
    protected Directory getEntity() {
        return dataProvider.directory();
    }

    @Override
    protected GenericMapper<Directory, DirectoryFullDto, DirectoryUpdateDto> getMapper() {
        return directoryMapper;
    }

    @Override
    protected GenericCRUDService<Directory, DirectoryFullDto, DirectoryUpdateDto> getMockedService() {
        return directoryService;
    }

    @Override
    @Test
    public void mapEntityToFullDto() {

        Directory directory = dataProvider.directoryWithProjectAndParent();
        DirectoryFullDto directoryFullDto = directoryMapper.mapEntityToFullDto(directory);

        assertThat(directory.getId()).isEqualTo(directoryFullDto.getId());
        assertThat(directory.getProjects().size()).isEqualTo(directoryFullDto.getProjectIds().size());
        assertThat(directory.getParentDirectory().getId()).isEqualTo(directoryFullDto.getParentDirectoryId());
        assertThat(directory.getTitle()).isEqualTo(directoryFullDto.getTitle());
        assertThat(directory.getDescription()).isEqualTo(directoryFullDto.getDescription());

    }

    @Override
    @Test
    public void mapFullDtoToEntity() {

        Directory directory = dataProvider.directoryWithProjectAndParent();
        DirectoryFullDto directoryFullDto = directoryMapper.mapEntityToFullDto(directory);

        when(directoryService.getEntity(anyLong())).thenReturn(directory.getParentDirectory());
        directory.getProjects().forEach(project ->
                when(projectService.getEntity(project.getId())).thenReturn(project));

        Directory mappedDirectory = directoryMapper.mapFullDtoToEntity(directoryFullDto);

        assertThat(directory).isEqualTo(mappedDirectory);

    }

    @Override
    @Test
    public void mapEntityToUpdateDto() {

        Directory directory = dataProvider.directoryWithProjectAndParent();

        DirectoryUpdateDto updateDto = directoryMapper.mapEntityToUpdateDto(directory);

        assertThat(updateDto.getParentDirectoryId()).isEqualTo(directory.getParentDirectory().getId());
        assertThat(updateDto.getTitle()).isEqualTo(directory.getTitle());
        assertThat(updateDto.getDescription()).isEqualTo(directory.getDescription());

    }

    @Override
    @Test
    public void mapForSaveEntity() {

        DirectoryUpdateDto updateDto = dataProvider.directoryUpdateDto();
        Directory parentDirectory = dataProvider.directory();
        updateDto.setParentDirectoryId(parentDirectory.getId());

        when(directoryService.getEntity(anyLong())).thenReturn(parentDirectory);

        Directory mappedDirectory = directoryMapper.mapForSaveEntity(updateDto);

        assertThat(mappedDirectory.getId()).isNull();
        assertThat(mappedDirectory.getParentDirectory().getId()).isEqualTo(updateDto.getParentDirectoryId());
        assertThat(mappedDirectory.getTitle()).isEqualTo(updateDto.getTitle());
        assertThat(mappedDirectory.getDescription()).isEqualTo(updateDto.getDescription());

    }

    @Override
    @Test
    public void mapForUpdateEntity() {

        Directory parentDirectory = dataProvider.directory();
        Directory directory = dataProvider.directory();
        directory.setParentDirectory(parentDirectory);

        DirectoryUpdateDto updateDto = directoryMapper.mapEntityToUpdateDto(directory);
        updateDto.setTitle("new title");

        when(directoryService.getEntity(anyLong())).thenReturn(parentDirectory);

        Directory mappedDirectory = directoryMapper.mapForUpdateEntity(directory, updateDto);

        assertThat(mappedDirectory.getId()).isEqualTo(directory.getId());

        assertThat(mappedDirectory.getParentDirectory().getId()).isEqualTo(updateDto.getParentDirectoryId());
        assertThat(mappedDirectory.getDescription()).isEqualTo(updateDto.getDescription());
        assertThat(mappedDirectory.getTitle()).isEqualTo(updateDto.getTitle());

    }
}
