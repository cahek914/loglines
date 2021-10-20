package org.kao.loglines.controller.task;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kao.loglines.LoglinesApplication;
import org.kao.loglines.data.TestDataProvider;
import org.kao.loglines.entity.Task;
import org.kao.loglines.exception.GenericServiceException;
import org.kao.loglines.service.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = LoglinesApplication.class)
class TaskControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TestDataProvider dataProvider;

    @MockBean
    private TaskService service;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getByIdIsOk() throws Exception {

        Task task = dataProvider.task(0);
        task.setId(1L);

        when(service.get(task.getId())).thenReturn(task);

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/{id}", task.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getByIdIsBadRequest() throws Exception {

        when(service.get(anyLong())).thenThrow(GenericServiceException.NotFound.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/{id}", anyLong()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}