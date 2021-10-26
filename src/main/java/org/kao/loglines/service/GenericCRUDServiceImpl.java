package org.kao.loglines.service;

import org.kao.loglines.entity.EntityId;
import org.kao.loglines.exception.GenericServiceException;
import org.kao.loglines.mapper.GenericMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class GenericCRUDServiceImpl<Entity extends EntityId, DtoUpdate> implements GenericCRUDService<Entity , DtoUpdate> {

    public abstract JpaRepository<Entity, Long> getRepository();

    public abstract GenericMapper<Entity, DtoUpdate> getMapper();

    @Override
    @Transactional(readOnly = true)
    public DtoUpdate get(Long id) {
        return getMapper().mapToDto(
                getRepository().findById(id).orElseThrow(() -> new GenericServiceException.NotFound(id))
        );
    }

    @Override
    public Entity getEntity(Long id) {
        return getRepository().findById(id).orElseThrow(() -> new GenericServiceException.NotFound(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Entity> getList() {
        return getRepository().findAll();
    }

    @Override
    @Transactional
    public Entity create(DtoUpdate dtoUpdate) {
        return getRepository().save(getMapper().mapToEntity(dtoUpdate));
    }

    @Override
    @Transactional
    public Entity update(Long id, DtoUpdate dtoUpdate) {
        return getRepository().save(
                getMapper().mapUpdateEntity(getEntity(id), dtoUpdate));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        getRepository().delete(getEntity(id));
    }
}
