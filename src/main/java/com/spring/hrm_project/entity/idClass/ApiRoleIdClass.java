package com.spring.hrm_project.entity.idClass;

import jakarta.persistence.Entity;
import lombok.Data;

import java.io.Serializable;

@Data
public class ApiRoleIdClass implements Serializable {

    private String apiId;

    private String roleId;

}
