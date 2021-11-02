package org.kao.loglines.controller.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kao.loglines.LoglinesApplication;
import org.kao.loglines.data.TestDataProvider;
import org.kao.loglines.dto.task.TaskFullDto;
import org.kao.loglines.entity.task.Task;
import org.kao.loglines.exception.GenericServiceException;
import org.kao.loglines.mapper.task.TaskMapper;
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

import java.util.List;

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
    private TaskMapper taskMapper;

    @MockBean
    private TaskService service;

    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @AfterEach
    void tearDown() {
        List<Task> taskListDb = service.getList();
        if (!taskListDb.isEmpty()) {
            taskListDb.forEach(task -> service.deleteById(task.getId()));
        }
        taskListDb = service.getList();
        assertThat(taskListDb).isEmpty();
    }

    @Test
    void getByIdIsOk() throws Exception {

        Task task = dataProvider.task();

        when(service.get(task.getId())).thenReturn(taskMapper.mapEntityToFullDto(task));

        mockMvc.perform(MockMvcRequestBuilders
                        .get(TASK_ID_URL, task.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getByNotExistIdReturnBadRequest() throws Exception {

        when(service.get(anyLong())).thenThrow(GenericServiceException.NotFound.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(TASK_ID_URL, anyLong()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void postValidData() throws Exception {

        Task task = dataProvider.task();
        String jsonBody = objectMapper.writeValueAsString(taskMapper.mapEntityToFullDto(task));

        when(service.create(any(TaskFullDto.class))).thenReturn(task);

        String response = mockMvc.perform(MockMvcRequestBuilders
                        .post(TASK_ROOT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Task taskDb = objectMapper.readValue(response, Task.class);

        assertThat(task.getId()).isEqualTo(taskDb.getId());
        assertThat(task.getTitle()).isEqualTo(taskDb.getTitle());
        assertThat(task.getDescription()).isEqualTo(taskDb.getDescription());
        assertThat(taskDb.getCreatedDate()).isNotNull();
        assertThat(taskDb.getUpdatedDate()).isNotNull();
    }

    @Test
    void postInvalidDataReturnBadRequest() throws Exception {

        Task task = dataProvider.task(10);
        String jsonBody = objectMapper.writeValueAsString(taskMapper.mapEntityToFullDto(task));

        mockMvc.perform(MockMvcRequestBuilders
                        .post(TASK_ROOT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void updateValidData() throws Exception {

        Task task = dataProvider.task();
        String jsonBody = objectMapper.writeValueAsString(taskMapper.mapEntityToFullDto(task));

        when(service.update(anyLong(), any(TaskFullDto.class))).thenReturn(task);

        String response = mockMvc.perform(MockMvcRequestBuilders
                        .put(TASK_ID_URL, task.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        Task taskDb = objectMapper.readValue(response, Task.class);
        assertThat(taskDb).isEqualTo(task);
    }

    @Test
    void updateInvalidDataReturnBadRequest() throws Exception {

        Task task = dataProvider.task(10);
        String jsonBody = objectMapper.writeValueAsString(taskMapper.mapEntityToFullDto(task));

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
