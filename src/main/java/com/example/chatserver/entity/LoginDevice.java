package com.example.chatserver.entity;

import com.example.chatserver.entity.enums.LoginDeviceStatusEnum;
import com.example.chatserver.validation.EnumValidator;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@Table(name = "login_device")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    @Nonnull
    private String name;

    @Column(name = "location", unique = true)
    @Nonnull
    private String location;

    @Column(name = "status")
    @Nonnull
    @Enumerated(EnumType.STRING)
    private LoginDeviceStatusEnum status;

    @Column(name = "created_date")
    @Nonnull
    private Long createdDate;

    @OneToMany(mappedBy="loginDevice")
    private Set<RefreshToken> refreshTokens;
}
