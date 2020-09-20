package com.xuefei.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import com.xuefei.utils.RabbitMQUtils;
import java.io.IOException;

/**
 * @description: 发送者
 * @author: xuefei
 * @date: 2020/09/20 19:50
 */
public class Provider {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = null;
        try {
            // 创建通道
            channel = connection.createChannel();
            // 通道绑定对象
            // 参数1：队列名称，如果队列不存在会自动创建
            // 参数2：用来定义队列特性是否要持久化
            // 参数3：exclusive 是否独占队列 true 独占队列 反之亦然
            // 参数4：autoDelete：是否在消费完成后自动删除队列 true 删除队列 反之亦然
            // 参数5：额外附加参数
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "hello world";
            // 发送消息
            // 参数1：交换机名称
            // 参数2：队列名称
            // 参数3：传递消息额外设置(例：MessageProperties.PERSISTENT_TEXT_PLAIN 会持久化消息)
            // 参数4：消息的具体内容
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        } catch (IOException e) {
            e.printStackTrace();
        }
        RabbitMQUtils.closeChannelAndConnection(channel, connection);
    }
}
