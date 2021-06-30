package com.syrbu.rabbitdemo.service;

import com.rabbitmq.client.Channel;
import com.syrbu.rabbitdemo.config.RabbitConfig;
import com.syrbu.rabbitdemo.message.SimpleMessage;
import com.syrbu.rabbitdemo.util.ParseUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Log4j2
@Component
public class RabbitConsumer {

    @RabbitListener(queues = RabbitConfig.MESSAGE_QUEUE)
    public void onMessage(SimpleMessage message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            log.info("Received a new message: {}", ParseUtil.parseObject(message));
            channel.basicNack(tag, false, false);
//            channel.basicAck(tag, false);
        } catch (IOException e) {
            log.error("Rejecting the message: {}", ParseUtil.parseObject(message), e);
        }
    }

}
