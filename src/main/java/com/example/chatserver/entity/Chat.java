package com.example.chatserver.entity;

import java.util.Set;

import com.example.chatserver.entity.enums.TypeChatEnum;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity()
@Table(name = "chat")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nonnull
    private String name;

    @Nonnull
    private String avatar;

    @Column(name="type_chat",nullable = false)
    @Enumerated(EnumType.STRING)
    @Nonnull
    private TypeChatEnum typeChat;

    private String description;

    @Column(name="max_member")
    @Nonnull
    private Integer maxMember;

    @Column(name="last_message_at")
    private Long lastMessageAt;

    @Column(name="created_date")
    @Nonnull
    private Long createdDate;

    @OneToMany(mappedBy="chat")
    private Set<ChatMember> chatMembers;

    @OneToMany(mappedBy="chat")
    private Set<Message> messages;
}
