package com.spring.hrm_project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "TB_USER")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "USER_PASSWORD")
    private String userPassword;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "ORG_ID")
    private String orgId;

    @Column(name = "USER_PHONE_NUMBER")
    private String userPhoneNumber;

    @Column(name = "USER_Position_CODE")
    private String userPositionCode;

    @Column(name = "USER_JOIN_DATE")
    private String userJoinDate;

    @Column(name = "CREATE_ID")
    private String createId;

    @Column(name = "CREATE_DATE")
    private String createDate;

    @Column(name = "UPDATE_ID")
    private String updateId;

    @Column(name = "UPDATE_DATE")
    private String updateDate;

    @Column(name = "USE_YN")
    private String useYn;

}
