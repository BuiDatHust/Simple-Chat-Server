package com.example.chatserver.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "message_sent")
public class MessageSent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="message_id")
    private Message message;

    @ManyToOne
    @JoinColumn(name="chat_member_id")
    private ChatMember chatMember;

    @Column(name = "created_date")
    @Nonnull
    private Integer createdDate;
}
