package com.self;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author: yds
 * @date: 2020/09/08
 */
public class ConsumerMain {
    public static final String self_direct_exchange = "self_direct_exchange";
    public static final String self_direct_queue = "self_direct_queue";
    public static final String self_direct_routingKey = "self_direct_routingKey";
    public static void main(String[] args) throws Exception {
        Address[] addresses = new Address[]{new Address("127.0.0.1",5672)};
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        Connection connection = connectionFactory.newConnection(addresses);
        final Channel channel = connection.createChannel();
        channel.basicQos(64);
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                long t1 = System.currentTimeMillis();
                System.out.println("-----"+t1+"-----begin");
                System.out.println("consumerTag="+consumerTag);
                System.out.println("envelope="+ JSONObject.toJSONString(envelope));
                System.out.println("properties="+ JSONObject.toJSONString(properties));
                System.out.println("body="+ new String(body,"UTF-8"));
                System.out.println("-----"+t1+"-----end");
                channel.basicAck(envelope.getDeliveryTag(), false);

            }
        };
        channel.basicConsume(self_direct_queue, consumer);
        TimeUnit.SECONDS.sleep(50000);
        channel.close();
        connection.close();
    }
}
