package com.spring.hrm_project.service;

import com.spring.hrm_project.entity.Overtime;
import com.spring.hrm_project.model.overtime.OvertimeRequest;
import com.spring.hrm_project.repository.OvertimeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.spring.hrm_project.utils.DateUtil.getCurrentDate;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OvertimeService {

    private final OvertimeRepository overtimeRepository;


    public boolean insertOvertime(OvertimeRequest overtimeRequest) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        overtimeRepository.save(
                Overtime.builder()
                .overtimeStartTime(overtimeRequest.getStartDate() + " " + overtimeRequest.getStartTime())
                .overtimeEndTime(overtimeRequest.getEndDate() + " " + overtimeRequest.getEndTime())
                .overtimeId(UUID.randomUUID().toString())
                .overtimeDescription(overtimeRequest.getOvertimeDescription())
                .userId(userId)
                .createId(userId)
                .createDate(getCurrentDate().toString())
                .useYn("N").build());
        return true;
    }
}
