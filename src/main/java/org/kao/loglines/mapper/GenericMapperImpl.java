package org.kao.loglines.mapper;

import org.kao.loglines.entity.EntityId;
import org.kao.loglines.service.GenericCRUDService;
import org.mapstruct.Named;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class GenericMapperImpl<Entity extends EntityId, DtoFull, DtoUpdate> implements GenericMapper<Entity, DtoFull, DtoUpdate> {

    protected abstract GenericCRUDService<Entity, DtoFull, DtoUpdate> getService();

    @Override
    public List<Long> mapEntitiesToIds(Collection<Entity> entities) {
        return entities.stream().map(EntityId::getId).collect(Collectors.toList());
    }

    @Override
    @Named("mapIdToEntity")
    public Entity mapIdToEntity(Long id) {
        return Objects.isNull(id) ? null : getService().getEntity(id);
    }

}
