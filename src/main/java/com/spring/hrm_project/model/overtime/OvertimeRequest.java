package com.spring.hrm_project.model.overtime;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class OvertimeRequest {
    private String startTime;
    private String startDate;
    private String endTime;
    private String endDate;
    private String overtimeDescription;
    private String fileId;
}
