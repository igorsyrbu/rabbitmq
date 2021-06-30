package com.syrbu.rabbitdemo.controller;

import com.syrbu.rabbitdemo.message.SimpleMessage;
import com.syrbu.rabbitdemo.service.RabbitProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
public class MessageController {

    private final RabbitProducer rabbitProducer;

    @PostMapping("/message")
    public void send(@RequestBody SimpleMessage message) {
        rabbitProducer.send(message);
    }

}
