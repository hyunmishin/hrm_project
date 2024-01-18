package com.spring.hrm_project.model.security;

import com.spring.hrm_project.model.security.SecurityApiDto;
import lombok.Data;

import java.util.List;

@Data
public class SecurityRoleDto {

    private String roleId;

    private List<SecurityApiDto> apiDtoList;

}
