package com.spring.hrm_project.entity;

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
public class Overtime {
    @Id
    private String overtimeId;
    private String userId;
    private String overtimeStartTime;
    private String overtimeEndTime;
    private String overtimeDescription;
    private String createId;
    private String createDate;
    private String updateId;
    private String updateDate;
    private String useYn;
}
