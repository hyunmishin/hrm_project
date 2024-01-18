package com.spring.hrm_project.model.login;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {

    private String userId;
    private String userPassword;
}
