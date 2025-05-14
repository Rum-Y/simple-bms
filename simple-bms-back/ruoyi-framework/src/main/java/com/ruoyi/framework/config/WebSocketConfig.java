package com.ruoyi.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author eddie
 * @version 1.0
 * @date 2025/5/13 19:48
 */
@Configuration
public class WebSocketConfig {
    /**
     * 注入一个ServerEndpointExporter,
     * 该Bean会自动注册使用@ServerEndpoint注解申明的websocket endpoint
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}