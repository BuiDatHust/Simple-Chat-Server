package com.example.chatserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.chatserver.entity.Otp;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long>  {
    @Modifying
    @Query(value = "update otp set is_active=false where type_otp=?1 and phone_number=?2", nativeQuery = true)
    void inActiveAllOldOtp(String typeOtp, String phoneNumber);

    @Query(value = "select * from otp where value=?1 and is_active=true and type_otp=?2 and phone_number=?3", nativeQuery = true)
    Otp findMatchTypeOtp(String value, String typeOtp, String phoneNumber); 

    @Query(value = "update otp set is_active = false where id=?1", nativeQuery = true)
    Otp inActiveOtpById(Long id);
}
