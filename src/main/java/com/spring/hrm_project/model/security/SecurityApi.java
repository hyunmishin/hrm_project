package com.spring.hrm_project.model.security;

import lombok.Data;

import java.util.List;

@Data
public class SecurityApi {

    private String apiUrl;

    private List<String> roleList;

}
