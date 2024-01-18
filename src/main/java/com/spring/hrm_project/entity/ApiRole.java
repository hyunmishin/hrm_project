package com.spring.hrm_project.entity;

import com.spring.hrm_project.entity.idClass.ApiRoleId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Data;

@Entity(name = "TB_API_ROLE")
@Data
@IdClass(ApiRoleId.class)
public class ApiRole {
    @Id
    @Column(name = "API_ID")
    private String apiId;
    @Id
    @Column(name = "ROLE_ID")
    private String roleId;

    private String createId;

    private String createDate;

    private String updateId;

    private String updateDate;
}
