package com.xuefei;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = RabbitmqSpringbootApplication.class)
@RunWith(SpringRunner.class)
class RabbitmqSpringbootApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // hello world
    @Test
    void testHello() {
        rabbitTemplate.convertAndSend("hello", "hello world");
    }

    // topic 动态路由  订阅模式
    @Test
    void testTopic() {
        rabbitTemplate.convertAndSend("topics", "user.save","发送user.save的key的路由");
    }

    // route 路由
    @Test
    void testRoute() {
        rabbitTemplate.convertAndSend("directs", "info","发送info的key的路由");
    }

    // fanout 广播
    @Test
    void testFanout() {
        rabbitTemplate.convertAndSend("logs", "","Fanout的模型发送的消息");
    }

    // work
    @Test
    void testWork() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("work", "hello work");
        }
    }
}
