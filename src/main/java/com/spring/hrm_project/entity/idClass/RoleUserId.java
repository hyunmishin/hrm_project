package com.spring.hrm_project.entity.idClass;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoleUserId implements Serializable {

    private String roleId;

    private String userId;
}
