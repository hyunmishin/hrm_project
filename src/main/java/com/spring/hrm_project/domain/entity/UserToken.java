package com.spring.hrm_project.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "user_token")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String accessToken;
    private String loginId;
    private String refresh_token;

}
