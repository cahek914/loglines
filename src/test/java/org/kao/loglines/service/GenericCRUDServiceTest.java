package org.kao.loglines.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.kao.loglines.data.DatabaseCleaner;
import org.kao.loglines.data.TestDataProvider;
import org.kao.loglines.entity.EntityId;
import org.kao.loglines.entity.TitleDescription;
import org.kao.loglines.exception.GenericServiceException;
import org.kao.loglines.mapper.GenericMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ValidationException;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public abstract class GenericCRUDServiceTest<
        Entity extends TitleDescription & EntityId,
        DtoFull extends TitleDescription & EntityId,
        DtoUpdate extends TitleDescription> {

    private static final int MINIMUM_ARRAY_SIZE = 2;

    @Autowired
    private TestDataProvider dataProvider;

    protected abstract Entity getEntity();

    protected abstract Entity getEntity(int maxSizeCorrector);

    protected abstract GenericMapper<Entity, DtoFull, DtoUpdate> getMapper();

    protected abstract GenericCRUDService<Entity, DtoFull, DtoUpdate> getService();

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @AfterEach
    public void tearDown() {
        databaseCleaner.clean();
    }

    @Test
    public void createdEntityListSizeShouldEqualToRetrieved() {

        List<DtoFull> fullDtos = fillDatabaseBySimpleEntities();
        assertThat(fullDtos.size()).isGreaterThanOrEqualTo(MINIMUM_ARRAY_SIZE);

        List<DtoFull> fullDtosDb = getService().getList();
        assertThat(fullDtosDb.size()).isEqualTo(fullDtos.size());

    }

    @Test
    public void requestedEntitiesByListIdsSizeShouldEqualToRetrieved() {

        List<DtoFull> fullDtos = fillDatabaseBySimpleEntities();
        assertThat(fullDtos.size()).isGreaterThanOrEqualTo(MINIMUM_ARRAY_SIZE);
        List<Long> ids = fullDtos.stream().map(DtoFull::getId).collect(Collectors.toList());

        List<DtoFull> fullDtosDb = getService().getList(ids);
        assertThat(fullDtosDb.size()).isEqualTo(fullDtos.size());

    }

    @Test
    public void updatedEntityTitleOrDescriptionShouldBeStoredToDatabase() {

        DtoUpdate dtoUpdate = getMapper().mapEntityToUpdateDto(getEntity());
        Long savedId = getService().create(dtoUpdate).getId();

        dtoUpdate.setTitle("some new title");
        dtoUpdate.setDescription("some new description");
        DtoFull dtoFullDb = getService().update(savedId, dtoUpdate);

        assertThat(dtoFullDb.getTitle()).isEqualTo(dtoUpdate.getTitle());
        assertThat(dtoFullDb.getDescription()).isEqualTo(dtoUpdate.getDescription());

    }

    @Test
    public void deletedEntityShouldNotExistInDatabase() {

        List<DtoFull> fullDtos = fillDatabaseBySimpleEntities();
        assertThat(fullDtos.size()).isGreaterThanOrEqualTo(MINIMUM_ARRAY_SIZE);

        Long idToDelete = fullDtos.get(fullDtos.size() / 2).getId();
        getService().deleteById(idToDelete);

        List<DtoFull> fullDtosDb = getService().getList();

        assertThat(fullDtosDb.size()).isEqualTo(fullDtos.size() - 1);
        assertThat(fullDtosDb.stream().anyMatch(dto -> dto.getId().equals(idToDelete))).isFalse();

    }

    @Test
    public void shouldThrownByValidationExceptionIfEntityTitleOrDescriptionIncreaseMaxSize() {

        Entity entity = getEntity(10);
        assertThatThrownBy(() -> getService().create(
                getMapper().mapEntityToUpdateDto(entity)))
                .isInstanceOf(ValidationException.class);

    }

    @Test
    public void shouldThrownExceptionIfEntityToGetByIdNotExist() {

        assertThatThrownBy(() -> getService().get(100000L))
                .isInstanceOf(GenericServiceException.NotFound.class);

    }

    @Test
    public void shouldThrownExceptionIfEntityToDeleteByIdNotExist() {

        assertThatThrownBy(() -> getService().deleteById(100000L))
                .isInstanceOf(GenericServiceException.NotFound.class);

    }

    private List<DtoFull> fillDatabaseBySimpleEntities() {
        return dataProvider.getRandomListOf(this::getEntity, 0, MINIMUM_ARRAY_SIZE, 10)
                .stream()
                .map(getMapper()::mapEntityToUpdateDto)
                .collect(Collectors.toList())
                .stream()
                .map(getService()::create)
                .collect(Collectors.toList());

    }

}
