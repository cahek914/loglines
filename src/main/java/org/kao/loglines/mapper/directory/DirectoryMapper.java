package org.kao.loglines.mapper.directory;

import org.kao.loglines.dto.directory.DirectoryFullDto;
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

@Mapper(componentModel = "spring", uses = {ProjectMapper.class, DirectoryService.class, ProjectService.class})
public abstract class DirectoryMapper extends GenericMapperImpl<Directory, DirectoryFullDto> {

    @Autowired
    private DirectoryService directoryService;

    @Override
    public GenericCRUDService<Directory, DirectoryFullDto> getService() {
        return directoryService;
    }

    @Override
    @Mapping(source = "parentDirectory.id", target = "parentDirectoryId")
    @Mapping(source = "projects", target = "projectIds")
    public abstract DirectoryFullDto mapToDto(Directory directory);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projects", ignore = true)
    @Mapping(source = "parentDirectoryId", target = "parentDirectory", qualifiedByName = "mapIdToEntity")
    public abstract Directory mapToEntity(DirectoryFullDto directoryFullDto);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projects", ignore = true)
    @Mapping(source = "parentDirectoryId", target = "parentDirectory", qualifiedByName = "mapIdToEntity")
    public abstract Directory mapUpdateEntity(@MappingTarget Directory directory, DirectoryFullDto directoryFullDto);

}
