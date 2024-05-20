package com.example.chatserver.repository;

import com.example.chatserver.entity.LoginDevice;
import com.example.chatserver.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    void deleteByValue(String value);

    void deleteAllByLoginDevice(LoginDevice loginDeviceId);
}
