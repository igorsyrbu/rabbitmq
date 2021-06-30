package com.syrbu.messages.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Profile("remote")
@Configuration
@EnableWebSocketMessageBroker
public class RabbitMqWebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${rabbit-stomp.host}")
    private String relayHost;
    @Value("${rabbit-stomp.port}")
    private Integer relayPort;
    @Value("${rabbit-stomp.login}")
    private String clientLogin;
    @Value("${rabbit-stomp.password}")
    private String clientPassword;

    private static final String WS_ENDPOINT = "/websocket";
    private static final String TOPIC_DESTINATION_PREFIX = "/topic";
    private static final String APP_DESTINATION_PREFIX = "/app";

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
        log.info("Configure message broker. Application destination prefix: {}. Broker destination prefix: {}. " +
                        "Broker relay host: {}. Broker relay port: {}",
                APP_DESTINATION_PREFIX, TOPIC_DESTINATION_PREFIX, relayHost, relayPort);
        registry
                .setApplicationDestinationPrefixes(APP_DESTINATION_PREFIX)
                .enableStompBrokerRelay(TOPIC_DESTINATION_PREFIX)
                .setRelayHost(relayHost)
                .setRelayPort(relayPort)
                .setClientLogin(clientLogin)
                .setClientPasscode(clientPassword);
    }

}
