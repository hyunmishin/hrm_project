package com.spring.hrm_project.entity;

import com.spring.hrm_project.entity.idClass.RoleUserIdClass;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Data;

@Entity(name = "TB_ROLE_USER")
@Data
@IdClass(RoleUserIdClass.class)
public class RoleUser {

    @Id
    private String roleId;

    @Id
    private String userId;

    private String createId;

    private String createDate;

    private String updateId;

    private String updateDate;

}
