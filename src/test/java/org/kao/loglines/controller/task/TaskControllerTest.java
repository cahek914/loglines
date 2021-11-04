package org.kao.loglines.controller.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kao.loglines.LoglinesApplication;
import org.kao.loglines.data.DatabaseCleaner;
import org.kao.loglines.data.TestDataProvider;
import org.kao.loglines.dto.task.TaskFullDto;
import org.kao.loglines.dto.task.TaskUpdateDto;
import org.kao.loglines.entity.task.Task;
import org.kao.loglines.exception.GenericServiceException;
import org.kao.loglines.mapper.task.TaskMapper;
import org.kao.loglines.service.project.ProjectService;
import org.kao.loglines.service.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = LoglinesApplication.class)
class TaskControllerTest {

    private static final String TASK_ROOT_URL = "/tasks";
    private static final String TASK_ID_URL = "/tasks/{id}";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TestDataProvider dataProvider;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private TaskMapper taskMapper;

    @MockBean
    private TaskService taskService;

    @MockBean
    private ProjectService projectService;

    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @AfterEach
    void tearDown() {
        databaseCleaner.clean();
    }

    @Test
    void getByIdIsOk() throws Exception {

        Task task = dataProvider.task();

        when(taskService.get(task.getId())).thenReturn(taskMapper.mapEntityToFullDto(task));

        mockMvc.perform(MockMvcRequestBuilders
                        .get(TASK_ID_URL, task.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getByNotExistIdReturnBadRequest() throws Exception {

        when(taskService.get(anyLong())).thenThrow(GenericServiceException.NotFound.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(TASK_ID_URL, anyLong()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void postValidData() throws Exception {

        Task task = dataProvider.task();
        TaskFullDto taskFullDto = taskMapper.mapEntityToFullDto(dataProvider.task());

        String jsonBody = objectMapper.writeValueAsString(taskMapper.mapEntityToUpdateDto(task));

        when(taskService.create(any(TaskUpdateDto.class))).thenReturn(taskFullDto);

        String response = mockMvc.perform(MockMvcRequestBuilders
                        .post(TASK_ROOT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn().getResponse().getContentAsString();

        TaskFullDto taskFullDtoDb = objectMapper.readValue(response, TaskFullDto.class);

        assertThat(taskFullDtoDb.getId()).isEqualTo(taskFullDto.getId());
        assertThat(taskFullDtoDb.getCreatedDate()).isNotNull();
        assertThat(taskFullDtoDb.getUpdatedDate()).isNotNull();
        assertThat(taskFullDtoDb.getTitle()).isEqualTo(taskFullDto.getTitle());
        assertThat(taskFullDtoDb.getDescription()).isEqualTo(taskFullDto.getDescription());
    }

    @Test
    void postInvalidDataReturnBadRequest() throws Exception {

        Task task = dataProvider.task(10);
        String jsonBody = objectMapper.writeValueAsString(taskMapper.mapEntityToUpdateDto(task));

        mockMvc.perform(MockMvcRequestBuilders
                        .post(TASK_ROOT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void updateValidData() throws Exception {

        Task task = dataProvider.task();
        TaskFullDto taskFullDto = taskMapper.mapEntityToFullDto(task);
        TaskUpdateDto taskUpdateDto = taskMapper.mapEntityToUpdateDto(task);

        String jsonBody = objectMapper.writeValueAsString(taskUpdateDto);

        when(taskService.update(anyLong(), any(TaskUpdateDto.class))).thenReturn(taskFullDto);

        String response = mockMvc.perform(MockMvcRequestBuilders
                        .put(TASK_ID_URL, task.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        TaskFullDto taskFullDtoDb = objectMapper.readValue(response, TaskFullDto.class);
        assertThat(taskFullDtoDb).isEqualTo(taskFullDto);
    }

    @Test
    void updateInvalidDataReturnBadRequest() throws Exception {

        Task task = dataProvider.task();
        task = dataProvider.setTitleAndDescription(task, 10);
        TaskUpdateDto taskUpdateDto = taskMapper.mapEntityToUpdateDto(task);

        String jsonBody = objectMapper.writeValueAsString(taskUpdateDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(TASK_ID_URL, task.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void deleteData() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(TASK_ID_URL, anyLong()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
