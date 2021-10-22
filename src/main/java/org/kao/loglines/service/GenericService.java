package org.kao.loglines.service;

import java.util.List;

public interface GenericService<Entity, DtoUpdate> {

    Entity get(Long id);

    List<Entity> getList();

    Entity create(DtoUpdate dtoUpdate);

    Entity update(Long id, DtoUpdate dtoUpdate);

    void deleteById(Long id);

}
