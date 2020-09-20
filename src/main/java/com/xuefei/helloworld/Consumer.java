package com.xuefei.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;

/**
 * @description: 消费者
 * @author: xuefei
 * @date: 2020/09/20 22:23
 */
public class Consumer {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("47.101.156.32");
        factory.setPort(5672);
        factory.setVirtualHost("/xuefei");
        factory.setUsername("xuefei");
        factory.setPassword("abcd");
        Connection connection = null;
        Channel channel = null;
        try {
            // 创建连接对象
            connection = factory.newConnection();
            // 创建通道
            channel = connection.createChannel();
            // 通道绑定对象
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            };
            // 消费消息
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}