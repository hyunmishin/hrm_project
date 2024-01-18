package com.spring.hrm_project.model.login;

import com.spring.hrm_project.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginUserInfo {

    private User data;

    private String jwtToken;

    private String desc;
}
