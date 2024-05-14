package com.example.chatserver.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "refresh_token")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value")
    @Nonnull
    private String value;

    @ManyToOne
    @JoinColumn(name="login_device_id")
    private LoginDevice loginDevice;

    @Column(name = "is_active")
    @Nonnull
    private boolean isActive;

    @Column(name = "expire_time")
    @Nonnull
    private Long expireTime;

    @Column(name = "created_date")
    @Nonnull
    private Long createdDate;
}
