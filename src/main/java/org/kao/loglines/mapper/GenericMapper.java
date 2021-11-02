package org.kao.loglines.mapper;

import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;

public interface GenericMapper<Entity, DtoFull, DtoUpdate> {

    List<Long> mapEntitiesToIds(Collection<Entity> entities);

    Entity mapIdToEntity(Long id);

    DtoFull mapEntityToFullDto(Entity entity);

    DtoUpdate mapEntityToUpdateDto(Entity entity);

    Entity mapFullDtoToEntity(DtoFull dtoFull);

    Entity mapForSaveEntity(DtoUpdate dtoUpdate);

    Entity mapForUpdateEntity(@MappingTarget Entity entity, DtoUpdate dtoUpdate);

}
