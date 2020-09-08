package com.producer.web;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author: yds
 * @date: 2020/09/08
 */
@RestController
@RequestMapping("/")
public class RabbitmqController {

    @Resource
    RabbitTemplate rabbitTemplate;
    @GetMapping("/direct/{name}")
    public String direct(@PathVariable String name) {
        String messageId = String.valueOf(UUID.randomUUID());
        Map<String,Object> bodyMap = new HashMap<String, Object>();
        bodyMap.put("messageId", messageId);
        bodyMap.put("content",name);
        bodyMap.put("timestamp",System.currentTimeMillis());
        rabbitTemplate.convertAndSend("directExchange","routingKey_1",bodyMap);
        return "ok";
    }
}
