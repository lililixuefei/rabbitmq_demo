package com.xuefei;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RabbitmqSpringbootApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // hello world
    @Test
    void contextLoads() {
        rabbitTemplate.convertAndSend("hello", "hello world");
    }

}
