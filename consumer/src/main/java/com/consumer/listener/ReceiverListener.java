package com.consumer.listener;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author: yds
 * @date: 2020/09/08
 */
@Component
@RabbitListener(queues = {"directQueueName"})
public class ReceiverListener {

    @RabbitHandler
    public void handle(Map bodyMap){
        System.out.println("DirectReceiver消费者收到消息  : " + JSONObject.toJSONString(bodyMap));
    }
}
