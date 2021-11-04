package org.kao.loglines.controller.directory;

import lombok.RequiredArgsConstructor;
import org.kao.loglines.controller.GenericCRUDController;
import org.kao.loglines.dto.directory.DirectoryFullDto;
import org.kao.loglines.dto.directory.DirectoryUpdateDto;
import org.kao.loglines.entity.directory.Directory;
import org.kao.loglines.service.GenericCRUDService;
import org.kao.loglines.service.directory.DirectoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/directories")
@RequiredArgsConstructor
public class DirectoryController extends GenericCRUDController<Directory, DirectoryFullDto, DirectoryUpdateDto> {

    private final DirectoryService directoryService;

    @Override
    public GenericCRUDService<Directory, DirectoryFullDto, DirectoryUpdateDto> getService() {
        return directoryService;
    }
}
