package com.chatapp.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE = "chat_queue";
    public static final String EXCHANGE = "chat_exchange";
    public static final String ROUTING_KEY = "chat_routingKey";

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    //PRODUCER CONFIGURATION FOR CONVERTING MESSAGE
    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter messageConverter ){

        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;

    }

    //CONSUMER CONFIGURATION FOR MESSAGE CONVERTING
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter messageConverter ) {

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        return factory;
    }

    @Bean
    public org.springframework.amqp.rabbit.core.RabbitAdmin rabbitAdmin(
            org.springframework.amqp.rabbit.connection.ConnectionFactory connectionFactory) {
        return new org.springframework.amqp.rabbit.core.RabbitAdmin(connectionFactory);
    }

//    @Bean
//    public org.springframework.boot.CommandLineRunner runner(
//            org.springframework.amqp.rabbit.core.RabbitAdmin rabbitAdmin,
//            Queue queue,
//            TopicExchange exchange,
//            Binding binding) {
//
//        return args -> {
//            System.out.println("🔥 Declaring RabbitMQ components...");
//
//            rabbitAdmin.declareQueue(queue);
//            rabbitAdmin.declareExchange(exchange);
//            rabbitAdmin.declareBinding(binding);
//
//            System.out.println("✅ Queue, Exchange, Binding declared");
//        };
//    }

    @Bean
    public Queue queue(){
        System.out.println("🔥 Queue Bean Created");
       return new Queue(QUEUE, true);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange){

        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(ROUTING_KEY);
    }

}
