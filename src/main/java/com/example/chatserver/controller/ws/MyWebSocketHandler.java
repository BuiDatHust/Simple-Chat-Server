package com.example.chatserver.controller.ws;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@RequiredArgsConstructor
public class MyWebSocketHandler extends TextWebSocketHandler {

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Handle messages
//        session.sendMessage(new TextMessage("Hello, " + session.getAttributes().get("phoneNumber")));
        messagingTemplate.convertAndSend("/chat", "response"+message);
    }
}