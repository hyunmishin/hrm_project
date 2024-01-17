package com.spring.hrm_project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class SecurityRoleDto {

    private String roleId;

    private List<SecurityApiDto> apiDtoList;

}
