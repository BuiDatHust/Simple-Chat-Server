package com.example.chatserver.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

import com.example.chatserver.entity.enums.UserChatStatusEnum;

@Data
@Entity
@Table(name = "user_chat")
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    private String fullname;

    @Column(name = "status")
    @Nonnull
    @Enumerated(EnumType.STRING)
    private UserChatStatusEnum status;

    private String username;

    private String avatar;

    private String bio;

    private Integer dob;

    private String address;

    @Column(name = "last_active_at")
    private Long lastActiveAt;

    @Column(name = "created_date")
    @Nonnull
    private Long createdDate;

    @OneToMany(mappedBy="user")
    private Set<ChatMember> chatMembers;

    @OneToOne(mappedBy = "user")
    private UserSetting useSetting;

    @OneToMany(mappedBy="user")
    private Set<LoginDevice> loginDevice;
}
