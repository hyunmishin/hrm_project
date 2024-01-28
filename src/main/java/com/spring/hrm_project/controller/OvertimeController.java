package com.spring.hrm_project.controller;

import com.spring.hrm_project.model.overtime.OvertimeRequest;
import com.spring.hrm_project.service.OvertimeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/overtime")
public class OvertimeController {

    private final OvertimeService overtimeService;

    @Operation(summary = "야근식대 조회", description = "추가한 야근식대 리스트 조회")
    @GetMapping("/list")
    public ResponseEntity<Void> geyOvertimeList() {
        return new ResponseEntity<Void>( HttpStatus.OK);
    }

    @Operation(summary = "야근식대 추가", description = "회사 규정에 따라 초과 근무를 했을 경우 야근식대 신청 진행")
    @PostMapping("/insert")
    public ResponseEntity<Boolean> insertOvertime(@RequestBody OvertimeRequest overtimeRequest) {
        return new ResponseEntity<Boolean>(overtimeService.insertOvertime(overtimeRequest), HttpStatus.OK);
    }

    @Operation(summary = "야근식대 삭제", description = "추가한 야근식대 삭제")
    @PostMapping("/delete/{overtimeId}")
    public ResponseEntity<Boolean> deleteOvertime(@PathVariable String overtimeId) {
        return new ResponseEntity<Boolean>( HttpStatus.OK);
    }

    @Operation(summary = "야근식대 수정", description = "회사 규정에 따라 초과 근무를 했을 경우 야근식대 수정 진행")
    @PostMapping("/update/{overtimeId}")
    public ResponseEntity<Void> updateOvertime(
            @PathVariable String overtimeId,
            @RequestBody OvertimeRequest overtimeRequest
    ) {
        return new ResponseEntity<Void>( HttpStatus.OK);
    }
}
