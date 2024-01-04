package com.spring.hrm_project.controller;

import com.spring.hrm_project.common.config.domain.dto.LoginRequest;
import com.spring.hrm_project.common.config.domain.entity.User;
import com.spring.hrm_project.service.UserService;
import com.spring.hrm_project.utils.JwtTokenUtil;
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
    public String login(@RequestBody LoginRequest loginRequest) {

        User user = userService.login(loginRequest);

        // 로그인 아이디나 비밀번호가 틀린 경우 global error return
        if(user == null) {
            return "로그인 아이디 또는 비밀번호가 틀렸습니다.";
        }

        // 로그인 성공 => Jwt Token 발급
        String secretKey = "my-secret-key-123123";
        long expireTimeMs = 1000 * 60 * 60;     // Token 유효 시간 = 60분

        String jwtToken = JwtTokenUtil.createToken(user.getLoginId(), secretKey, expireTimeMs);

        // 토큰 생성 후 access_token 을 usr_token에 login_id와 토큰을 저장함

        return jwtToken;
    }

    @GetMapping("/info")
    public ResponseEntity<String> userInfo() {

        User loginUser = userService.getLoginUserByLoginId(SecurityContextHolder.getContext().getAuthentication().getName());

        return new ResponseEntity<>(String.format("loginId : %s\nnickname : %s\nrole : %s",
                loginUser.getLoginId(), loginUser.getNickname(), loginUser.getUserRole()), HttpStatus.OK);

    }
}
