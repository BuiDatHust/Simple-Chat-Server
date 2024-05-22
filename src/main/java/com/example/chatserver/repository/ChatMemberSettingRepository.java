package com.example.chatserver.repository;

import com.example.chatserver.entity.ChatMemberSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMemberSettingRepository extends JpaRepository<ChatMemberSetting, Long> {
}
