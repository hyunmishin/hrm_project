package com.spring.hrm_project.model.login;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private String userId;

    private String userName;

    private String orgId;

    private String userPositionCode;

}
