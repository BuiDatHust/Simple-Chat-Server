package com.example.chatserver.service.chat;

import com.example.chatserver.entity.Chat;
import com.example.chatserver.entity.ChatMember;
import com.example.chatserver.entity.ChatMemberSetting;
import com.example.chatserver.entity.User;
import com.example.chatserver.helper.datetime.DateTimeHelper;
import com.example.chatserver.repository.ChatMemberRepository;
import com.example.chatserver.repository.ChatMemberSettingRepository;
import com.example.chatserver.repository.ChatRepository;
import com.example.chatserver.service.chat.dto.InitChatData;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class ChatServiceImpl implements  ChatService {
    private final ChatRepository chatRepository;
    private final ChatMemberRepository chatMemberRepository;
    private final ChatMemberSettingRepository chatMemberSettingRepository;

    @Override
    @Transactional
    public Chat initChat(InitChatData initChatData) {
        Chat chat = Chat.builder()
                .typeChat(initChatData.getTypeChatEnum())
                .name(initChatData.getName())
                .description(initChatData.getDescription())
                .avatar(initChatData.getAvatar())
                .createdDate(DateTimeHelper.getCurrentTimeUtcMs())
                .build();
        chat = chatRepository.save(chat);
        Set<ChatMember> chatMembers = new HashSet<>();
        for(User user: initChatData.getUsers()) {
            ChatMember chatMember = ChatMember.builder()
                    .name(user.getFullname())
                    .user(user)
                    .createdDate(DateTimeHelper.getCurrentTimeUtcMs())
                    .build();
            chatMember = chatMemberRepository.save(chatMember);

            ChatMemberSetting chatMemberSetting = ChatMemberSetting.builder()
                    .isPushNotify(true)
                    .chatMember(chatMember)
                    .createdDate(DateTimeHelper.getCurrentTimeUtcMs())
                    .build();
            chatMemberSetting = chatMemberSettingRepository.save(chatMemberSetting);

            chatMember.setChatMemberSetting(chatMemberSetting);
            chatMembers.add(chatMember);
        }
        chat.setChatMembers(chatMembers);

        return chat;
    }
}
