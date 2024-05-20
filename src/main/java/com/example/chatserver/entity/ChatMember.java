package com.example.chatserver.entity;

import java.util.Set;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chat_member")
@Builder
public class ChatMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="chat_id")
    private Chat chat;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    private String name;

    @Column(name="created_date")
    @Nonnull
    private Long createdDate;

    @OneToOne(mappedBy = "chatMember")
    private ChatMemberSetting chatMemberSetting;

    @OneToMany(mappedBy="senderMember")
    private Set<Message> messages;

    @OneToMany(mappedBy="chatMember")
    private Set<MessageSent> messageSents;
}
