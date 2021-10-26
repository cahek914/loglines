package org.kao.loglines.service;

import java.util.List;

public interface GenericCRUDService<Entity, DtoUpdate> {

    DtoUpdate get(Long id);

    Entity getEntity(Long id);

    List<Entity> getList();

    Entity create(DtoUpdate dtoUpdate);

    Entity update(Long id, DtoUpdate dtoUpdate);

    void deleteById(Long id);

}
