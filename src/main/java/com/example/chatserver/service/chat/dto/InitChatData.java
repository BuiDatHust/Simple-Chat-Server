package com.example.chatserver.service.chat.dto;

import com.example.chatserver.entity.User;
import com.example.chatserver.entity.enums.TypeChatEnum;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class InitChatData {
    private TypeChatEnum typeChatEnum;
    private String name;
    private String avatar;
    private String description;
    private List<User> users;
}
