package com.example.chatserver.repository;

import com.example.chatserver.entity.User;
import com.example.chatserver.entity.enums.UserChatStatusEnum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select u from User u where u.phoneNumber=?1 order by u.id limit 1")
    User findOneByPhoneNumber(String phoneNumber);

    User findOneByUsername(String username);

    @Query(value = "select u from User u where u.phoneNumber=?1 and u.status=?2 order by u.id limit 1")
    User findOneByPhoneNumberAndStatus(String phoneNumber, UserChatStatusEnum status);
}
