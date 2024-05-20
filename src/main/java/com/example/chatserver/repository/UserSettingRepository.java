package com.example.chatserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.chatserver.entity.UserSetting;

@Repository
public interface UserSettingRepository extends JpaRepository<UserSetting, Long> {

}
