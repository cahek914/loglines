package org.kao.loglines.service;

import java.util.Collection;
import java.util.List;

public interface GenericCRUDService<Entity, DtoFull, DtoUpdate> {

    DtoFull get(Long id);

    Entity getEntity(Long id);

    List<Entity> getList();

    List<Entity> getList(Collection<Long> ids);

    Entity create(DtoUpdate dtoUpdate);

    Entity update(Long id, DtoUpdate dtoUpdate);

    void deleteById(Long id);

}
