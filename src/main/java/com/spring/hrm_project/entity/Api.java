package com.spring.hrm_project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "TB_API")
@Data
public class Api {
    @Id
    @Column(name = "API_ID")
    private String apiId;

    private String apiName;

    @Column(name = "API_URL")
    private String apiUrl;

    private String apiType;

    private String createId;

    private String createDate;

    private String updateId;

    private String updateDate;

}
