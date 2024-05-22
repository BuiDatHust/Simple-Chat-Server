package com.example.chatserver.configuration.security;

import com.example.chatserver.controller.ws.MyWebSocketHandler;
import com.example.chatserver.controller.ws.TestChat;
import com.example.chatserver.helper.jwt.JwtHelper;
import com.example.chatserver.interceptor.AuthHandshakeInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketSecurityConfig implements WebSocketConfigurer {
    private final JwtHelper jwtHelper;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(new MyWebSocketHandler(messagingTemplate), "/ws")
                .addHandler(new TestChat(messagingTemplate), "/chat")
                .addInterceptors(new AuthHandshakeInterceptor(jwtHelper))
                .setAllowedOrigins("*");
    }
}