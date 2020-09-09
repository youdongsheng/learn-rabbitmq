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
        consume();
    }

    public static void consume() throws Exception{
        // 1、通过address方式 创建连接
        Address[] addresses = new Address[]{new Address("127.0.0.1",5672)};
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        Connection connection = connectionFactory.newConnection(addresses);

        // 2、创建channel
        final Channel channel = connection.createChannel();
        // 3、设置消息获取的数量
        channel.basicQos(64);
        // 4、创建消费者
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

        // 5、消费者 订阅 queue
        channel.basicConsume(self_direct_queue, consumer);
        TimeUnit.SECONDS.sleep(50000); //休眠保证消费者能够完全消费消息
        // 6、关闭channel和connection
        channel.close();
        connection.close();
    }
}
