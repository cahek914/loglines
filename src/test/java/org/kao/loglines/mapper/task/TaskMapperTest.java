package org.kao.loglines.mapper.task;

import org.junit.jupiter.api.Test;
import org.kao.loglines.data.TestDataProvider;
import org.kao.loglines.entity.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TaskMapperTest {

    @Autowired
    private TestDataProvider dataProvider;

    @Autowired
    private TaskMapper mapper;

    @Test
    void mappedIdsSizeShouldEqualsToEntitiesSize() {

        List<Task> taskList = dataProvider.getRandomListOf(dataProvider::task, 0, 2, 10);
        List<Long> ids = mapper.mapEntitiesToIds(taskList);

        assertThat(taskList.size()).isEqualTo(ids.size());

    }

    @Test
    void mappingToUpdateDto() {

//        Task task = dataProvider.taskFull(0);
//        TaskUpdateDto updateDto = mapper.mapToDto(task);
//
//        assertThat(task.getTitle()).isEqualTo(updateDto.getTitle());
//        assertThat(task.getDescription()).isEqualTo(updateDto.getDescription());

    }
}