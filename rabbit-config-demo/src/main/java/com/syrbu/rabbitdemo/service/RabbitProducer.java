package com.syrbu.rabbitdemo.service;

import com.syrbu.rabbitdemo.message.SimpleMessage;
import com.syrbu.rabbitdemo.util.ParseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static com.syrbu.rabbitdemo.config.RabbitConfig.MESSAGE_EXCHANGE;
import static com.syrbu.rabbitdemo.config.RabbitConfig.MESSAGE_ROUTING_KEY;
import static org.springframework.amqp.core.MessageProperties.CONTENT_TYPE_JSON;
import static org.springframework.messaging.MessageHeaders.CONTENT_TYPE;

@Log4j2
@Component
@RequiredArgsConstructor
public class RabbitProducer {

    private final RabbitTemplate rabbitTemplate;

    public void send(SimpleMessage message) {
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        message.setId(UUID.randomUUID().toString());
        message.setReceivedAt(currentTime);
        log.info("Publishing a new message: {}", ParseUtil.parseObject(message));

        rabbitTemplate.convertAndSend(MESSAGE_EXCHANGE, MESSAGE_ROUTING_KEY, message, this::updateHeaders);
    }

    private Message updateHeaders(Message message) {
        message.getMessageProperties().getHeaders().put(CONTENT_TYPE, CONTENT_TYPE_JSON);
        return message;
    }

}
