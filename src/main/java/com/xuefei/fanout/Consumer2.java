package com.xuefei.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.xuefei.utils.RabbitMQUtils;


/**
 * @description: 消费者2
 * @author: xuefei
 * @date: 2020/09/23 23:30
 */
public class Consumer2 {
    public static void main(String[] args) {
        // 获取连接对象
        Connection connection = RabbitMQUtils.getConnection();

        try {
            // 创建通道
            Channel channel = connection.createChannel();
            // 通道绑定交换机
            channel.exchangeDeclare("logs", "fanout");
            // 临时队列
            String queue = channel.queueDeclare().getQueue();
            // 绑定交换机和队列
            channel.queueBind(queue, "logs", "");
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" 消费者[2] Received '" + message + "'");
            };
            // 消费信息
            channel.basicConsume(queue, true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
