package com.xuefei.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @description: 发送者
 * @author: xuefei
 * @date: 2020/09/20 19:50
 */
public class Provider {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) {
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
            String message = "hello world";
            // 发送消息
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        try {
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }

}
