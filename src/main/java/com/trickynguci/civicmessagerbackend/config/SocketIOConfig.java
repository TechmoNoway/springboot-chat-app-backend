package com.trickynguci.civicmessagerbackend.config;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;


@Configuration
public class SocketIOConfig {

    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname("localhost");
        config.setPort(4000);
        return new SocketIOServer(config);
    }
}