package com.example.chatserver.repository;

import com.example.chatserver.entity.LoginDevice;
import com.example.chatserver.entity.enums.LoginDeviceStatusEnum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginDeviceRepository extends JpaRepository<LoginDevice, Long> {
    LoginDevice findOneLoginDeviceByNameAndUserId(String name, Long userId);

    @Modifying
    @Query("update LoginDevice ld set ld.status = ?1 where ld.phoneNumber = ?2 and ld.deviceName = ?3")
    void updateStatusByPhoneNumberAndDevice( LoginDeviceStatusEnum loginDeviceStatusEnum, String phoneNumber, String deviceName);
}
