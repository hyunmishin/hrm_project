package com.spring.hrm_project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "TB_ROLE")
@Data
public class Role {

    @Id
    @Column(name = "ROLE_ID")
    private String roleId;

    private String roleName;

    private String createId;

    private String createDate;

    private String updateId;

    private String updateDate;

    private String useYn;


}
