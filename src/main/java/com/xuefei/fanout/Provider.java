package com.xuefei.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.xuefei.utils.RabbitMQUtils;

import java.io.IOException;

/**
 * @description: 提供者
 * @author: xuefei
 * @date: 2020/09/23 23:22
 */
public class Provider {
    public static void main(String[] args) {
        // 获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        try {
            // 创建通道
            Channel channel = connection.createChannel();
            // 将通道声明指定交换机  参数1：交换机名称    参数2：交换机类型   fanout 广播类型
            channel.exchangeDeclare("logs", "fanout");
            // 发送消息
            channel.basicPublish("logs", "", null, "fanout type message".getBytes());

            // 释放资源
            RabbitMQUtils.closeChannelAndConnection(channel, connection);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
