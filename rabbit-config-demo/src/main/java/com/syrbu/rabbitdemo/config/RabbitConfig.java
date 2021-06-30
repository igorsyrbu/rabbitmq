package com.syrbu.rabbitdemo.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class RabbitConfig {

    public static final String MESSAGE_QUEUE = "messageQueue";
    public static final String MESSAGE_EXCHANGE = "messageExchange";
    public static final String MESSAGE_ROUTING_KEY = "messageRoutingKey";

    public static final String DEAD_LETTER_QUEUE = "deadLetterQueue";
    public static final String DEAD_LETTER_EXCHANGE = "deadLetterExchange";
    public static final String DEAD_LETTER_ROUTING_KEY = "deadLetterRoutingKey";

    @PostConstruct
    public void initQueues() {
        RabbitAdmin rabbitAdmin = rabbitAdmin();

        rabbitAdmin.declareQueue(messageQueue());
        rabbitAdmin.declareExchange(messageExchange());
        rabbitAdmin.declareBinding(messageBinding());

        rabbitAdmin.declareQueue(deadLetterQueue());
        rabbitAdmin.declareExchange(deadLetterExchange());
        rabbitAdmin.declareBinding(deadLetterBinding());
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    public Exchange messageExchange() {
        return ExchangeBuilder
                .directExchange(MESSAGE_EXCHANGE)
                .durable(true)
                .build();
    }

    public Queue messageQueue() {
        return QueueBuilder
                .durable(MESSAGE_QUEUE)
                .deadLetterExchange(DEAD_LETTER_EXCHANGE)
                .build();
    }

    public Binding messageBinding() {
        return BindingBuilder
                .bind(messageQueue())
                .to(messageExchange())
                .with(MESSAGE_ROUTING_KEY)
                .noargs();
    }

    public Exchange deadLetterExchange() {
        return ExchangeBuilder
                .fanoutExchange(DEAD_LETTER_EXCHANGE)
                .durable(true)
                .build();
    }

    public Queue deadLetterQueue() {
        return QueueBuilder
                .durable(DEAD_LETTER_QUEUE)
                .lazy()
                .build();
    }

    public Binding deadLetterBinding() {
        return BindingBuilder
                .bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with(DEAD_LETTER_ROUTING_KEY)
                .noargs();
    }

}
