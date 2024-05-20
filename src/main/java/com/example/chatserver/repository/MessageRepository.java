package com.example.chatserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.chatserver.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}
