package com.xuefei.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @description: 工具类
 * @author: xuefei
 * @date: 2020/09/20 23:17
 */
public class RabbitMQUtils {
    private static ConnectionFactory factory;

    static {
        // 重量级资源，类加载的时候只执行一次
        factory = new ConnectionFactory();
        factory.setHost("47.101.156.32");
        factory.setPort(5672);
        factory.setVirtualHost("/xuefei");
        factory.setUsername("xuefei");
        factory.setPassword("abcd");
    }

    // 定义提供连接对象的方法
    public static Connection getConnection() {
        try {
            return factory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 关闭通道和连接的方法
    public static void closeChannelAndConnection(Channel channel, Connection connection) {
        try {
            if (channel != null)
                channel.close();
            if (connection != null)
                connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
