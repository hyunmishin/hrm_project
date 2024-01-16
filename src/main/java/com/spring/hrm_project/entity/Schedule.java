package com.spring.hrm_project.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Schedule {

    @Id
    String scheduleId;

    String scheduleStartDate;

    String scheduleEndDate;

    String scheduleCode;

    String scheduleName;

    String scheduleDescription;

    String userId;

    String privateYn;

    String createId;

    String createDate;

    String updateId;

    String updateDate;

    String  useYn;

}
