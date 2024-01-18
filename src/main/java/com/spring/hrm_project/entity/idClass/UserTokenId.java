package com.spring.hrm_project.entity.idClass;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserTokenId implements Serializable {

    private String userId;

    private String userAccessToken;

    private String userRefreshToken;

}
