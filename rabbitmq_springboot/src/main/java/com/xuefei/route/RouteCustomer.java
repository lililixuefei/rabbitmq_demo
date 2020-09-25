package com.xuefei.route;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @description: 消费者
 * @author: xuefei
 * @date: 2020/09/25 23:04
 */
@Component
public class RouteCustomer {

    @RabbitListener(bindings = {@QueueBinding(value = @Queue, exchange = @Exchange(value = "direct", type = "direct"), key = {"info", "error", "warn"})})
    public void receive1(String message) {
        System.out.println("message1 = " + message);
    }

    @RabbitListener(bindings = {@QueueBinding(value = @Queue, exchange = @Exchange(value = "direct", type = "direct"), key = {"error"})})
    public void receive2(String message) {
        System.out.println("message2 = " + message);
    }
}
