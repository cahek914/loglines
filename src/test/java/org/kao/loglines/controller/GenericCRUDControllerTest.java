package org.kao.loglines.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kao.loglines.entity.EntityId;
import org.kao.loglines.exception.GenericServiceException;
import org.kao.loglines.mapper.GenericMapper;
import org.kao.loglines.service.GenericCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public abstract class GenericCRUDControllerTest<Entity extends EntityId,
        DtoFull extends EntityId, DtoUpdate>  {

    protected abstract Class<DtoFull> getDtoFullClass();

    protected abstract Class<DtoUpdate> getDtoUpdateClass();

    protected abstract String getRootUrl();

    protected abstract String getUrlWithId();

    protected abstract Entity getEntity();

    protected abstract Entity getEntity(int maxSizeCorrector);

    protected abstract GenericMapper<Entity, DtoFull, DtoUpdate> getMapper();

    protected abstract GenericCRUDService<Entity, DtoFull, DtoUpdate> getService();

    @Autowired
    private WebApplicationContext webApplicationContext;

    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    public void getByIdIsOk() throws Exception {

        Entity entity = getEntity();
        DtoFull dtoFull = getMapper().mapEntityToFullDto(entity);

        when(getService().get(anyLong())).thenReturn(dtoFull);

        mockMvc.perform(MockMvcRequestBuilders
                .get(getUrlWithId(), anyLong()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(getService()).get(anyLong());
    }

    @Test
    void getByNotExistIdReturnBadRequest() throws Exception {

        when(getService().get(anyLong())).thenThrow(GenericServiceException.NotFound.class);

        mockMvc.perform(MockMvcRequestBuilders
                .get(getUrlWithId(), anyLong()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        verify(getService()).get(anyLong());
    }

    @Test
    void postValidData() throws Exception {

        Entity entity = getEntity();
        DtoFull dtoFull = getMapper().mapEntityToFullDto(entity);

        String jsonBody = objectMapper.writeValueAsString(getMapper().mapEntityToUpdateDto(entity));

        when(getService().create(any(getDtoUpdateClass()))).thenReturn(dtoFull);

        String response = mockMvc.perform(MockMvcRequestBuilders
                .post(getRootUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn().getResponse().getContentAsString();

        verify(getService()).create(any(getDtoUpdateClass()));

        DtoFull dtoFullDb = objectMapper.readValue(response, getDtoFullClass());
        assertThat(dtoFullDb).isEqualTo(dtoFull);
    }

    @Test
    void postInvalidDataReturnBadRequest() throws Exception {

        Entity entity = getEntity(10);
        String jsonBody = objectMapper.writeValueAsString(getMapper().mapEntityToUpdateDto(entity));

        mockMvc.perform(MockMvcRequestBuilders
                .post(getRootUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        verify(getService(), never()).create(any(getDtoUpdateClass()));
    }

    @Test
    void updateValidData() throws Exception {

        Entity entity = getEntity();
        DtoFull dtoFull = getMapper().mapEntityToFullDto(entity);
        DtoUpdate dtoUpdate = getMapper().mapEntityToUpdateDto(entity);

        String jsonBody = objectMapper.writeValueAsString(dtoUpdate);

        when(getService().update(anyLong(), any(getDtoUpdateClass()))).thenReturn(dtoFull);

        String response = mockMvc.perform(MockMvcRequestBuilders
                .put(getUrlWithId(), eq(anyLong()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        verify(getService()).update(anyLong(), any(getDtoUpdateClass()));

        DtoFull dtoFullDb = objectMapper.readValue(response, getDtoFullClass());
        assertThat(dtoFullDb).isEqualTo(dtoFull);
    }

    @Test
    void updateInvalidDataReturnBadRequest() throws Exception {

        Entity entity = getEntity(10);
        DtoUpdate dtoUpdate = getMapper().mapEntityToUpdateDto(entity);

        String jsonBody = objectMapper.writeValueAsString(dtoUpdate);

        mockMvc.perform(MockMvcRequestBuilders
                .put(getUrlWithId(), 10L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        verify(getService(), never()).update(anyLong(), any(getDtoUpdateClass()));
    }

    @Test
    void deleteData() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .delete(getUrlWithId(), anyLong()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(getService()).deleteById(anyLong());
    }
}
