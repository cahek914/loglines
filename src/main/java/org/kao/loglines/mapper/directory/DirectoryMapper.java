package org.kao.loglines.mapper.directory;

import org.kao.loglines.model.directory.DirectoryFullDto;
import org.kao.loglines.model.directory.DirectoryUpdateDto;
import org.kao.loglines.entity.directory.Directory;
import org.kao.loglines.mapper.GenericMapperImpl;
import org.kao.loglines.mapper.project.ProjectMapper;
import org.kao.loglines.service.GenericCRUDService;
import org.kao.loglines.service.directory.DirectoryService;
import org.kao.loglines.service.project.ProjectService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {ProjectMapper.class, ProjectService.class, DirectoryService.class})
public abstract class DirectoryMapper extends GenericMapperImpl<Directory, DirectoryFullDto, DirectoryUpdateDto> {

    @Autowired
    private DirectoryService directoryService;

    @Override
    protected GenericCRUDService<Directory, DirectoryFullDto, DirectoryUpdateDto> getService() {
        return directoryService;
    }

    @Override
    @Mapping(source = "parentDirectory.id", target = "parentDirectoryId")
    @Mapping(source = "projects", target = "projectIds")
    public abstract DirectoryFullDto mapEntityToFullDto(Directory directory);

    @Override
    @Mapping(source = "id", target = "id")
    @Mapping(source = "projectIds", target = "projects")
    @Mapping(source = "parentDirectoryId", target = "parentDirectory", qualifiedByName = "mapIdToEntity")
    public abstract Directory mapFullDtoToEntity(DirectoryFullDto directoryFullDto);

    @Override
    @Mapping(source = "parentDirectory.id", target = "parentDirectoryId")
    public abstract DirectoryUpdateDto mapEntityToUpdateDto(Directory directory);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projects", ignore = true)
    @Mapping(source = "parentDirectoryId", target = "parentDirectory", qualifiedByName = "mapIdToEntity")
    public abstract Directory mapForSaveEntity(DirectoryUpdateDto directoryUpdateDto);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projects", ignore = true)
    @Mapping(source = "parentDirectoryId", target = "parentDirectory", qualifiedByName = "mapIdToEntity")
    public abstract Directory mapForUpdateEntity(@MappingTarget Directory directory, DirectoryUpdateDto directoryUpdateDto);

}
