package org.kao.loglines.service;

import org.kao.loglines.exception.GenericServiceException;
import org.kao.loglines.mapper.GenericMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class GenericCRUDServiceImpl<Entity, DtoFull, DtoUpdate>
        implements GenericCRUDService<Entity, DtoFull, DtoUpdate> {

    public abstract JpaRepository<Entity, Long> getRepository();

    public abstract GenericMapper<Entity, DtoFull, DtoUpdate> getMapper();

    @Override
    @Transactional(readOnly = true)
    public DtoFull get(Long id) {
        return getMapper().mapEntityToFullDto(
                getRepository().findById(id).orElseThrow(() -> new GenericServiceException.NotFound(id))
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Entity getEntity(Long id) {
        return getRepository().findById(id).orElseThrow(() -> new GenericServiceException.NotFound(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DtoFull> getList() {
        return getRepository().findAll().stream()
                .map(getMapper()::mapEntityToFullDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DtoFull> getList(Collection<Long> ids) {
        return getRepository().findAllById(ids).stream()
                .map(getMapper()::mapEntityToFullDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DtoFull create(DtoUpdate dtoUpdate) {
        return getMapper().mapEntityToFullDto(
                getRepository().save(
                        getMapper().mapForSaveEntity(dtoUpdate)));
    }

    @Override
    @Transactional
    public DtoFull update(Long id, DtoUpdate dtoUpdate) {
        return getMapper().mapEntityToFullDto(
                // flushes the data immediately during the execution
                // to get actual update data stamp
                getRepository().saveAndFlush(
                        getMapper().mapForUpdateEntity(getEntity(id), dtoUpdate)));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        getRepository().delete(getEntity(id));
    }
}
