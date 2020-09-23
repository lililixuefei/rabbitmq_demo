package com.xuefei.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.xuefei.utils.RabbitMQUtils;

import java.io.IOException;

/**
 * @description: 提供者
 * @author: xuefei
 * @date: 2020/09/24 00:10
 */
public class Provider {

    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) {
        // 获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        // 获取连接通道对象
        try {
            Channel channel = connection.createChannel();
            // 通过通道声明交换机  参数1：交换机名称   参数2：路由模式
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            // 发送消息
//            String routekey = "info";
            String routekey = "error";
            channel.basicPublish(EXCHANGE_NAME, routekey, null, ("这是direct模型发布的基于Route Key：" + routekey + "的消息").getBytes());
            RabbitMQUtils.closeChannelAndConnection(channel, connection);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
