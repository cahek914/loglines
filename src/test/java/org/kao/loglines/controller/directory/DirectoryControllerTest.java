package org.kao.loglines.controller.directory;

import org.kao.loglines.controller.GenericCRUDControllerTest;
import org.kao.loglines.data.TestDataProvider;
import org.kao.loglines.dto.directory.DirectoryFullDto;
import org.kao.loglines.dto.directory.DirectoryUpdateDto;
import org.kao.loglines.entity.directory.Directory;
import org.kao.loglines.mapper.GenericMapper;
import org.kao.loglines.mapper.directory.DirectoryMapper;
import org.kao.loglines.service.GenericCRUDService;
import org.kao.loglines.service.directory.DirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class DirectoryControllerTest extends GenericCRUDControllerTest<Directory, DirectoryFullDto, DirectoryUpdateDto> {

    private static final String URL_ROOT = "/directories";
    private static final String URL_ID = "/directories/{id}";

    @Autowired
    private TestDataProvider dataProvider;

    @MockBean
    private DirectoryService directoryService;

    @Autowired
    private DirectoryMapper directoryMapper;

    @Override
    protected Class<DirectoryFullDto> getDtoFullClass() {
        return DirectoryFullDto.class;
    }

    @Override
    protected Class<DirectoryUpdateDto> getDtoUpdateClass() {
        return DirectoryUpdateDto.class;
    }

    @Override
    protected String getRootUrl() {
        return URL_ROOT;
    }

    @Override
    protected String getUrlWithId() {
        return URL_ID;
    }

    @Override
    protected Directory getEntity() {
        return dataProvider.directory(0);
    }

    @Override
    protected Directory getEntity(int maxSizeCorrector) {
        return dataProvider.directory(maxSizeCorrector);
    }

    @Override
    protected GenericMapper<Directory, DirectoryFullDto, DirectoryUpdateDto> getMapper() {
        return directoryMapper;
    }

    @Override
    protected GenericCRUDService<Directory, DirectoryFullDto, DirectoryUpdateDto> getService() {
        return directoryService;
    }
}
