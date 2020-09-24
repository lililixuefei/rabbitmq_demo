package com.xuefei.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.xuefei.utils.RabbitMQUtils;

import java.io.IOException;


/**
 * @description: 消费者2
 * @author: xuefei
 * @date: 2020/09/24 22:16
 */
public class Consumer2 {
    public static void main(String[] args) {
        try {
            // 获取连接对象
            Connection connection = RabbitMQUtils.getConnection();
            // 创建管道
            Channel channel = connection.createChannel();
            // 声明交换机以及交换机类型
            channel.exchangeDeclare("topics", "topic");
            // 创建一个临时队列
            String queue = channel.queueDeclare().getQueue();
            // 绑定队列和交换机 动态通配符形式route key
            channel.queueBind(queue, "topics", "user.#");
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" 消费者[2] Received '" + message + "'");
            };
            // 消费信息
            channel.basicConsume(queue, true, deliverCallback, consumerTag -> {
            });

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
