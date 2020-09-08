package com.self;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: yds
 * @date: 2020/09/08
 */
public class ProducerMain {
    public static final String self_direct_exchange = "self_direct_exchange";
    public static final String self_direct_queue = "self_direct_queue";
    public static final String self_direct_routingKey = "self_direct_routingKey";
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(self_direct_exchange,"direct");
        channel.queueDeclare(self_direct_queue,false,false,false,null);
        channel.queueBind(self_direct_queue,self_direct_exchange,self_direct_routingKey);
        String message = "hello world";
        channel.basicPublish(self_direct_exchange,self_direct_routingKey, MessageProperties.TEXT_PLAIN,message.getBytes("UTF-8"));
        channel.close();
        connection.close();
    }
}
