package com.example.chatserver.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "user_chat")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_number")
    @Nonnull
    private String phoneNumber;

    @Column(name = "country_code")
    @Nonnull
    private String countryCode;

    @Nonnull
    private String fullname;

    @Nonnull
    private String username;

    private String avatar;

    private String bio;

    private Integer dob;

    private String address;

    @Column(name = "last_active_at")
    private Integer lastActiveAt;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_date")
    @Nonnull
    private Integer createdDate;

    @OneToMany(mappedBy="user")
    private Set<ChatMember> chatMembers;

    @OneToOne(mappedBy = "user")
    private UserSetting useSetting;
}
