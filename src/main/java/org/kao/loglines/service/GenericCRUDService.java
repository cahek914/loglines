package org.kao.loglines.service;

import java.util.Collection;
import java.util.List;

public interface GenericCRUDService<Entity, DtoFull, DtoUpdate> {

    DtoFull get(Long id);

    Entity getEntity(Long id);

    List<DtoFull> getList();

    List<DtoFull> getList(Collection<Long> ids);

    DtoFull create(DtoUpdate dtoUpdate);

    DtoFull update(Long id, DtoUpdate dtoUpdate);

    void deleteById(Long id);

}
