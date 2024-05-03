package com.example.chatserver.entity;

import com.example.chatserver.entity.enums.TypeOtp;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "otp")
public class Otp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_otp")
    @Nonnull
    @Enumerated(EnumType.STRING)
    private TypeOtp typeOtp;

    @Column(name = "is_active")
    private boolean isActive;

    @Nonnull
    private String value;

    @Column(name = "expire_at")
    @Nonnull
    private Integer expireAt; 

    @Column(name = "created_date")
    @Nonnull
    private Integer createdDate;
}
