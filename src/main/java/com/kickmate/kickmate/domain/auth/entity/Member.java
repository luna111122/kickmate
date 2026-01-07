package com.kickmate.kickmate.domain.auth.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(
        name = "member",
        indexes = {
                @Index(name = "idx_member_user_id", columnList = "user_id", unique = true)
        }
)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // PK (auto increment)

    @Column(name = "user_id", nullable = false, unique = true, length = 50)
    private String userId;  // 로그인 ID

    @Column(nullable = false, length = 255)
    private String passwordHash; // BCrypt

    public static Member create(String userId, String passwordHash) {
        return Member.builder()
                .userId(userId)
                .passwordHash(passwordHash)
                .build();
    }

}

