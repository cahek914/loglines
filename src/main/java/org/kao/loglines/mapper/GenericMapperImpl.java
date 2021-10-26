package org.kao.loglines.mapper;

import org.kao.loglines.entity.EntityId;
import org.kao.loglines.exception.GenericServiceException;
import org.kao.loglines.service.GenericCRUDService;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class GenericMapperImpl<Entity extends EntityId, DtoUpdate> implements GenericMapper<Entity, DtoUpdate> {

    public abstract GenericCRUDService<Entity, DtoUpdate> getService();

    @Override
    public List<Long> mapEntitiesToIds(Collection<Entity> entities) {
        return entities.stream().map(EntityId::getId).collect(Collectors.toList());
    }

    @Override
    @Named("mapIdsToEntities")
    public List<Entity> mapIdsToEntities(Collection<Long> ids) {
        if ( ids == null ) {
            return null;
        }
        List<Entity> entities = new ArrayList<>(ids.size());
        for ( Long id : ids ) {
            entities.add( mapIdToEntity(id) );
        }
        return entities;
    }

    @Override
    @Named("mapIdToEntity")
    public Entity mapIdToEntity(Long id) {
        return Objects.isNull(id) ? null : getService().getEntity(id);
    }

}
