package org.kao.loglines.service.directory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.kao.loglines.data.DatabaseCleaner;
import org.kao.loglines.data.TestDataProvider;
import org.kao.loglines.dto.directory.DirectoryFullDto;
import org.kao.loglines.dto.directory.DirectoryUpdateDto;
import org.kao.loglines.entity.directory.Directory;
import org.kao.loglines.mapper.GenericMapper;
import org.kao.loglines.mapper.directory.DirectoryMapper;
import org.kao.loglines.service.GenericCRUDService;
import org.kao.loglines.service.GenericCRUDServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DirectoryServiceTest extends GenericCRUDServiceTest<Directory, DirectoryFullDto, DirectoryUpdateDto> {

    @Autowired
    private TestDataProvider dataProvider;

    @Autowired
    private DirectoryService directoryService;

    @Autowired
    private DirectoryMapper directoryMapper;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        databaseCleaner.clean();
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
