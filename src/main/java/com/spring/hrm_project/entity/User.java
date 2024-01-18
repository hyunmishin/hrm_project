package com.spring.hrm_project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String userId;

    private String userPassword;

    private String userName;

    private String orgId;

    private String userPhoneNumber;

    private String userPositionCode;

    private String userJoinDate;

    private String createId;

    private String createDate;

    private String updateId;

    private String updateDate;

    private String useYn;

}
