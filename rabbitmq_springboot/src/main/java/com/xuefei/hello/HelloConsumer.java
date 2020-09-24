package com.xuefei.hello;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @description: 消费者
 * @author: xuefei
 * @date: 2020/09/24 23:04
 */
@Component
// 默认 持久化  非独占  不是自动删除队列
@RabbitListener(queuesToDeclare = @Queue(value = "hello",declare = "false",autoDelete = "true"))
public class HelloConsumer {

    @RabbitHandler
    public void receivel(String message){
        System.out.println("message = " + message);
    }
}
