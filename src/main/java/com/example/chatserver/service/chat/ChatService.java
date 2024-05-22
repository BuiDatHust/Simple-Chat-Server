package com.example.chatserver.service.chat;

import com.example.chatserver.entity.Chat;
import com.example.chatserver.service.chat.dto.InitChatData;

public interface ChatService {
    Chat initChat(InitChatData initChatData);
}
