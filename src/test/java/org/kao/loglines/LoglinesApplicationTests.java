package org.kao.loglines;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LoglinesApplicationTests {

    @Autowired
    private ApplicationContext context;

    @Test
    void contextLoads() {
        assertThat(context).isNotNull();
        assertThat(context.getBean("taskRepository")).isNotNull();
        assertThat(context.getBean("taskServiceImpl")).isNotNull();
    }

}
