package org.kao.loglines.mapper;

import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;

public interface GenericMapper<Entity, DtoUpdate> {

    List<Long> mapEntitiesToIds(Collection<Entity> entities);

    List<Entity> mapIdsToEntities(Collection<Long> ids);

    Entity mapIdToEntity(Long id);

    DtoUpdate mapToDto(Entity entity);

    Entity mapToEntity(DtoUpdate dtoUpdate);

    Entity mapUpdateEntity(@MappingTarget Entity entity, DtoUpdate dtoUpdate);

}
