package com.example.chatserver.repository;

import com.example.chatserver.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository  extends JpaRepository<Chat, Long> {
}
