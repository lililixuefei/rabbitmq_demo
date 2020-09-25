package com.xuefei.topic;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @description: 消费者
 * @author: xuefei
 * @date: 2020/09/25 22:57
 */
@Component
public class TopicConstomer {


    @RabbitListener(bindings = {@QueueBinding(value = @Queue, exchange = @Exchange(type = "topics"), key = {"user.save", "user.*"})})
    public void receive1(String message) {
        System.out.println("message1 =" + message);
    }

    @RabbitListener(bindings = {@QueueBinding(value = @Queue, exchange = @Exchange(type = "topics"), key = {"order.#", "produce.#"})})
    public void receive2(String message) {
        System.out.println("message2 =" + message);
    }


}
