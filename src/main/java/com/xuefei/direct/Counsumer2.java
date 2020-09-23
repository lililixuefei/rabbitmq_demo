package com.xuefei.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.xuefei.utils.RabbitMQUtils;

import java.io.IOException;

/**
 * @description: 消费者2
 * @author: xuefei
 * @date: 2020/09/24 00:19
 */
public class Counsumer2 {

    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = null;
        try {
            channel = connection.createChannel();
            // 通道声明交换机以及交换机的类型
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            // 创建一个临时队列
            String queue = channel.queueDeclare().getQueue();
            // 基于Route Key绑定队列和交换机
            channel.queueBind(queue, EXCHANGE_NAME, "info");
            channel.queueBind(queue, EXCHANGE_NAME, "error");
            channel.queueBind(queue, EXCHANGE_NAME, "warning");
                DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" 消费者[1] Received '" + message + "'");
            };
            // 消费信息
            channel.basicConsume(queue, true, deliverCallback, consumerTag -> {
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
