package com.spring.hrm_project.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Schedule {

    @Id
    private String scheduleId;

    private String scheduleStartDate;

    private String scheduleEndDate;

    private String scheduleCode;

    private String scheduleName;

    private String scheduleDescription;

    private String userId;

    private String privateYn;

    private String createId;

    private String createDate;

    private String updateId;

    private String updateDate;

    private String  useYn;

}
