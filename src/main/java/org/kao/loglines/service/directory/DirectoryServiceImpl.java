package org.kao.loglines.service.directory;

import lombok.RequiredArgsConstructor;
import org.kao.loglines.model.directory.DirectoryFullDto;
import org.kao.loglines.model.directory.DirectoryUpdateDto;
import org.kao.loglines.entity.directory.Directory;
import org.kao.loglines.mapper.GenericMapper;
import org.kao.loglines.mapper.directory.DirectoryMapper;
import org.kao.loglines.repository.DirectoryRepository;
import org.kao.loglines.service.GenericCRUDServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DirectoryServiceImpl extends GenericCRUDServiceImpl<Directory, DirectoryFullDto, DirectoryUpdateDto> implements DirectoryService {

    private final DirectoryRepository repository;

    @Autowired
    private DirectoryMapper mapper;

    @Override
    public JpaRepository<Directory, Long> getRepository() {
        return repository;
    }

    @Override
    public GenericMapper<Directory, DirectoryFullDto, DirectoryUpdateDto> getMapper() {
        return mapper;
    }

}
