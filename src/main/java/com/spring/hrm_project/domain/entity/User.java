package com.spring.hrm_project.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "user")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "login_id")
    private String loginId;
    private String password;
    private String nickname;

    @Column(name = "user_role")
    private String userRole;

    @Column(name = "access_token")
    private String accessToken;

    // OAuth 로그인에 사용
//    private String provider;
//    private String providerId;

}
