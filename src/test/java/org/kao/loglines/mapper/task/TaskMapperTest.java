package org.kao.loglines.mapper.task;

import org.junit.jupiter.api.Test;
import org.kao.loglines.data.TestDataProvider;
import org.kao.loglines.dto.task.TaskUpdateDto;
import org.kao.loglines.entity.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TaskMapperTest {

    @Autowired
    private TestDataProvider dataProvider;

    @Autowired
    private TaskMapper mapper;

    @Test
    void mappingTaskToUpdateDto() {

        Task task = dataProvider.taskFull(0);
        TaskUpdateDto updateDto = mapper.map(task);

        assertThat(task.getTitle()).isEqualTo(updateDto.getTitle());
        assertThat(task.getDescription()).isEqualTo(updateDto.getDescription());

    }
}