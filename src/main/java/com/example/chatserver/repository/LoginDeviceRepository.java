package com.example.chatserver.repository;

import com.example.chatserver.entity.LoginDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginDeviceRepository extends JpaRepository<LoginDevice, Long> {
    LoginDevice findOneLoginDeviceByNameAndUserId(String name, Long userId);
}
