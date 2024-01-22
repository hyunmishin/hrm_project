package com.spring.hrm_project.controller;

import com.spring.hrm_project.model.login.LoginRequest;
import com.spring.hrm_project.model.login.LoginResponse;
import com.spring.hrm_project.model.login.LoginUserInfo;
import com.spring.hrm_project.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/jwt-login")
public class LoginController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {

        LoginUserInfo loginUserInfo = userService.login(loginRequest);

        if(loginUserInfo == null) {
            return new ResponseEntity<>("아이디 또는 비밀번호가 맞지 않습니다.", HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(loginUserInfo.getJwtToken(), HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity<LoginResponse> userInfo() {

        LoginResponse loginResponse = userService.getUserInfoByUserId(SecurityContextHolder.getContext().getAuthentication().getName());

        return new ResponseEntity<>(loginResponse, HttpStatus.OK);

    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest httpServletRequest) {

        userService.logout(httpServletRequest);

        return new ResponseEntity<>("로그아웃 처리 되었습니다.", HttpStatus.OK);

    }

    @GetMapping("/admin")
    public ResponseEntity<String> adminPage() {
        return new ResponseEntity<>("관리자 페이지 접근 성공", HttpStatus.OK);
    }

    @GetMapping("/admin/approve")
    public ResponseEntity<String> adminApprove() {
        return new ResponseEntity<>("관리자 결재 페이지 접근 성공", HttpStatus.OK);
    }

}
