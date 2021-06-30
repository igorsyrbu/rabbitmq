package com.syrbu.messages.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Profile("local")
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    @Qualifier("messageBrokerTaskScheduler")
    private TaskScheduler scheduler;

    private static final String WS_ENDPOINT = "/websocket";
    private static final String TOPIC_DESTINATION_PREFIX = "/topic";
    private static final String APP_DESTINATION_PREFIX = "/app";
    private static final long[] HEARTBEAT_VALUE = new long[]{10000, 10000};

    @Bean
    public Map<Long, String> userSessions() {
        return new HashMap<>();
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        log.info("Register stomp endpoints: {}", WS_ENDPOINT);
        registry
                .addEndpoint(WS_ENDPOINT)
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        log.info("Configure message broker. Application destination prefix: {}. Broker destination prefix: {}. Heartbeat value: {}",
                APP_DESTINATION_PREFIX, TOPIC_DESTINATION_PREFIX, Arrays.toString(HEARTBEAT_VALUE));
        registry
                .setApplicationDestinationPrefixes(APP_DESTINATION_PREFIX)
                .enableSimpleBroker(TOPIC_DESTINATION_PREFIX)
                .setHeartbeatValue(HEARTBEAT_VALUE)
                .setTaskScheduler(scheduler);
    }


}
