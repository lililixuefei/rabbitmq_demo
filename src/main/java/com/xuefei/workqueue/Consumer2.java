package com.xuefei.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.xuefei.utils.RabbitMQUtils;

import java.io.IOException;

/**
 * @description: 消费者
 * @author: xuefei
 * @date: 2020/09/20 22:23
 */
public class Consumer2 {
    private final static String QUEUE_NAME = "work";

    public static void main(String[] argv) throws Exception {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = null;
        try {
            // 创建通道
            channel = connection.createChannel();
            // 通道绑定对象
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" 消费者[2] Received '" + message + "'");
            };
            // 消费消息
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}