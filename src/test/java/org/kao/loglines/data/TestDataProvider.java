package org.kao.loglines.data;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.kao.loglines.configuration.SizeType;
import org.kao.loglines.entity.Task;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
public class TestDataProvider {

    public <T> List<T> getRandomListOf(Function<Integer, T> createEntity, Integer maxSizeCorrector, Integer minListSize, Integer listSizeDelta) {

        List<T> list = new ArrayList<>();
        for(int i = 0; i < (int) (Math.random() * listSizeDelta) + minListSize; i++) {
            list.add(createEntity.apply(maxSizeCorrector));
        }
        return list;
    }

    public Task task(int maxSizeCorrector) {
        Task task = new Task();
        task.setTitle(RandomString.make(SizeType.TASK_TITLE_MAX_SIZE + maxSizeCorrector));
        task.setDescription(RandomString.make(SizeType.TASK_DESCRIPTION_MAX_SIZE + maxSizeCorrector));
        return task;
    }

}
