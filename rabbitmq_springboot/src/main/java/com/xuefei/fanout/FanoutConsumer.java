package com.xuefei.fanout;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @description: 消费者
 * @author: xuefei
 * @date: 2020/09/25 22:05
 */
@Component
public class FanoutConsumer {

    /**
     * @param message
     * @Queue 创建临时队列
     * @Exchange 绑定交换机
     */
    @RabbitListener(bindings = {@QueueBinding(value = @Queue, exchange = @Exchange(value = "logs", type = "fanout"))})
    public void receive1(String message) {
        System.out.println("message1 = " + message);
    }

    @RabbitListener(bindings = {@QueueBinding(value = @Queue, exchange = @Exchange(value = "logs", type = "fanout"))})
    public void receive2(String message) {
        System.out.println("message2 = " + message);
    }

}
