package org.kao.loglines.mapper;

import org.junit.jupiter.api.Test;
import org.kao.loglines.entity.EntityId;
import org.kao.loglines.entity.TitleDescription;
import org.kao.loglines.service.GenericCRUDService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class GenericMapperImplTest<Entity extends TitleDescription & EntityId, DtoFull, DtoUpdate> implements GenericMapperTest {

    protected abstract Entity getEntity();

    protected abstract GenericMapper<Entity, DtoFull, DtoUpdate> getMapper();

    protected abstract GenericCRUDService<Entity, DtoFull, DtoUpdate> getMockedService();

    @Test
    public void mapEntitiesToIds() {
        List<Entity> entities = new ArrayList<>();
        entities.add(getEntity());
        entities.add(getEntity());
        List<Long> ids = getMapper().mapEntitiesToIds(entities);

        assertThat(entities.size()).isEqualTo(ids.size());
    }

    @Test
    public void mapIdToEntity() {
        Entity entity = getEntity();
        Long id = entity.getId();

        when(getMockedService().getEntity(id)).thenReturn(entity);

        Entity mappedEntity = getMapper().mapIdToEntity(id);

        assertThat(mappedEntity.getId()).isEqualTo(entity.getId());
        assertThat(mappedEntity.getTitle()).isEqualTo(entity.getTitle());
        assertThat(mappedEntity.getDescription()).isEqualTo(entity.getDescription());
    }
}
