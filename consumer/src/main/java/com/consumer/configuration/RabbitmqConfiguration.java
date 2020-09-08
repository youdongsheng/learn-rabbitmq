package com.consumer.configuration;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: yds
 * @date: 2020/09/08
 */
@Configuration
public class RabbitmqConfiguration {

    @Bean
    public Queue directQueue(){
        return new Queue("directQueueName");
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("directExchange");
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(directQueue()).to(directExchange()).with("routingKey_1");
    }
}
