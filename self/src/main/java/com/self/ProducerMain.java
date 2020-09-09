package com.self;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.rmi.server.ExportException;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author: yds
 * @date: 2020/09/08
 */
public class ProducerMain {
    public static final String self_direct_exchange = "self_direct_exchange";
    public static final String self_direct_queue = "self_direct_queue";
    public static final String self_direct_routingKey = "self_direct_routingKey";
    public static void main(String[] args) throws Exception {
        deleteQueue();
    }

    public static void sendMsg() throws Exception {
        // 1、创建connection
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        Connection connection = connectionFactory.newConnection();
        // 2、创建channel
        Channel channel = connection.createChannel();
        // 3、创建 exchange
        channel.exchangeDeclare(self_direct_exchange,"direct", true,true,null);
        // 4、创建queue
        channel.queueDeclare(self_direct_queue,false,false,false,null);
        // 5、将 exchange 和 queue 进行绑定
        channel.queueBind(self_direct_queue,self_direct_exchange,self_direct_routingKey);
        // 6、发送message，携带routingKey 到指定的exchange
        String message = "hello world";
        channel.basicPublish(self_direct_exchange,self_direct_routingKey, MessageProperties.TEXT_PLAIN,message.getBytes("UTF-8"));
        // 7、关闭channel和connection
        channel.close();
        connection.close();
    }

    public static void deleteExchange() throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        AMQP.Exchange.DeleteOk result = channel.exchangeDelete(self_direct_exchange);
        System.out.println(result);
        channel.close();
        connection.close();
    }

    public static void deleteQueue() throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDelete(self_direct_queue);
        channel.close();
        connection.close();
    }
}
