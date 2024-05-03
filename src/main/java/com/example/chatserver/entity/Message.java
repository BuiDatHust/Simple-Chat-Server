package com.example.chatserver.entity;

import java.util.Set;

import com.example.chatserver.entity.enums.TypeContentMessage;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nonnull
    private String content;
    
    @Column(name = "content_type")
    @Enumerated(EnumType.STRING)
    private TypeContentMessage contentType;

    @Column(name = "is_sent")
    private boolean isSent;

    @Column(name = "is_hide_with_sender")
    private boolean isHideWithSender;

    @Column(name = "is_hide_with_everyone")
    private boolean isHideWithEveryone;

    @ManyToOne
    @JoinColumn(name="sender_member_id")
    private ChatMember senderMember;

    @ManyToOne
    @JoinColumn(name="chat_id")
    private Chat chat;

    @Column(name = "created_date")
    @Nonnull
    private Integer createdDate;

    @Column(name = "updated_date")
    @Nonnull
    private Integer updatedDate;

    @OneToMany(mappedBy="message")
    private Set<MessageSent> messageSents;
}
