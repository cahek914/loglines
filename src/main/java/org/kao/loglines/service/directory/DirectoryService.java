package org.kao.loglines.service.directory;

import org.kao.loglines.model.directory.DirectoryFullDto;
import org.kao.loglines.model.directory.DirectoryUpdateDto;
import org.kao.loglines.entity.directory.Directory;
import org.kao.loglines.service.GenericCRUDService;

public interface DirectoryService extends GenericCRUDService<Directory, DirectoryFullDto, DirectoryUpdateDto> {
}
