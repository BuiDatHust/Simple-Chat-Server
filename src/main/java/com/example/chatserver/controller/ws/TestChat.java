package com.example.chatserver.controller.ws;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@RequiredArgsConstructor
public class TestChat extends TextWebSocketHandler {
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("WebSocket connection established: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Handle messages
//        messagingTemplate.convertAndSend("/chat/"+session.getAttributes().get("userID"), "response"+message);
        System.out.println( "testchat " + session.getAttributes().get("userID"));
        messagingTemplate.convertAndSend("/recieve-chat", "response"+message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("WebSocket connection closed: " + session.getId());
    }
}
