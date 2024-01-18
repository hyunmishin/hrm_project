package com.spring.hrm_project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Api {
    @Id
    private String apiId;

    private String apiName;

    private String apiUrl;

    private String apiType;

    private String createId;

    private String createDate;

    private String updateId;

    private String updateDate;

}
