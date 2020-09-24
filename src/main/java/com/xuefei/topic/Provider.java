package com.xuefei.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.xuefei.utils.RabbitMQUtils;

import java.io.IOException;

/**
 * @description: 提供者
 * @author: xuefei
 * @date: 2020/09/24 22:15
 */
public class Provider {
    public static void main(String[] args) throws IOException {
        // 获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        // 声明交换机以及交换机类型
        channel.exchangeDeclare("topics", "topic");
        // 发布消息
        String routeKey = "user.save.findAll";
        channel.basicPublish("topics", routeKey, null, ("这里是topic动态路由模型，routeKey：[" + routeKey + "]").getBytes());
        // 关闭资源
        RabbitMQUtils.closeChannelAndConnection(channel, connection);
    }
}
