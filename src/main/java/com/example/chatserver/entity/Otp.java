package com.example.chatserver.entity;

import com.example.chatserver.entity.enums.TypeOtpEnum;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "otp")
@NoArgsConstructor
@AllArgsConstructor
public class Otp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_otp")
    @Nonnull
    @Enumerated(EnumType.STRING)
    private TypeOtpEnum typeOtp;

    @Column(name = "is_active")
    private boolean isActive;

    @Nonnull
    private String value;

    @Column(name = "phone_number")
    @Nonnull
    private String phoneNumber;

    @Column(name = "expire_at")
    @Nonnull
    private Long expireAt; 

    @Column(name = "created_date")
    @Nonnull
    private Long createdDate;
}
