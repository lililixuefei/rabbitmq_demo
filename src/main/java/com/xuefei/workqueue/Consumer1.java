package com.xuefei.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.xuefei.utils.RabbitMQUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @description: 消费者
 * @author: xuefei
 * @date: 2020/09/20 22:23
 */
public class Consumer1 {
    private final static String QUEUE_NAME = "work";

    public static void main(String[] argv) throws Exception {
        // 获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        try {
            // 创建通道
            final Channel channel = connection.createChannel();
            // 通道绑定对象
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(" 消费者[1] Received '" + message + "'");
                // 参数1：确认队列中哪个具体消息  参数2：是否开启多个消息同时确认
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            };
            // 消费消息
            channel.basicQos(1);  // 每次只能消费一个消息
            /**
             * 参数1：队列名称   参数2：消息自动确认 true 消费者自动向RabbitMQ确认消息消费  false 不会自动确认消息
             */
            channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag -> {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}