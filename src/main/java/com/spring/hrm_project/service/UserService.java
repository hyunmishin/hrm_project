package com.spring.hrm_project.service;

import com.spring.hrm_project.entity.User;
import com.spring.hrm_project.entity.UserToken;
import com.spring.hrm_project.model.login.LoginRequest;
import com.spring.hrm_project.model.login.LoginResponse;
import com.spring.hrm_project.model.login.LoginUserInfo;
import com.spring.hrm_project.repository.UserRepository;
import com.spring.hrm_project.repository.UserTokenRepository;
import com.spring.hrm_project.utils.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.spring.hrm_project.utils.DateUtil.getCurrentDate;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserTokenRepository userTokenRepository;

    public static final String SECRET_KEY = "my-secret-key-123123";
    public static final long EXPIRE_TIME_MS = 1000 * 60 * 60;       // Token 유효 시간 = 60분

    // 현재 날짜
    LocalDateTime currentDate = getCurrentDate();

    /**
     * 로그인 기능
     */
    public LoginUserInfo login(LoginRequest loginRequest) {

        Optional<User> optionalUser = userRepository.findByUserId(loginRequest.getUserId());

        // 로그인아이디와 일치하는 유저가 없으면 널값 리턴
        if(optionalUser.isEmpty()) {
            return null;
        }

        User userInfo = optionalUser.get();

        // 찾아온 유저의 패스워드와 입력된 패스워드가 다르면 널값 리턴
        if(!userInfo.getUserPassword().equals(loginRequest.getUserPassword())) {
            return null;
        }

        // 로그인 성공 => Jwt Token 발급
        String jwtToken = JwtTokenUtil.createToken(userInfo.getUserId(), SECRET_KEY, EXPIRE_TIME_MS);

        Optional<UserToken> optionalUserToken = userTokenRepository.findByUserIdAndUseYn(userInfo.getUserId(), "Y");

        if(optionalUserToken.isPresent()) {
            optionalUserToken.get().setUseYn("N");
            optionalUserToken.get().setUpdateDate(currentDate.toString());
            log.info("업데이트 완료");
        }

        UserToken userToken = UserToken.builder()
                .userId(userInfo.getUserId())
                .userAccessToken(jwtToken)
                .userRefreshToken(jwtToken)
                .createDate(currentDate.toString())
                .useYn("Y")
                .build();
        userTokenRepository.save(userToken);
        log.info("저장 완료");

        return LoginUserInfo.builder()
                .jwtToken(jwtToken)
                .data(userInfo).build();
    }

    /**
     * 인증, 인가 시 사용
     */
    public LoginResponse getUserInfoByUserId(String userId) {

        if(userId == null) {
            return null;
        }

        Optional<User> optionalUser = userRepository.findByUserId(userId);

        return LoginResponse.builder()
                .userId(userId)
                .userName(optionalUser.get().getUserName())
                .orgId(optionalUser.get().getOrgId())
                .userPositionCode(optionalUser.get().getUserPositionCode())
                .build();

    }

    /**
     * 로그아웃 기능
     */
    public void logout(HttpServletRequest httpServletRequest) {

        // 로그인한 사용자 정보 가져와서 토큰 사용여부 "N"으로 업데이트 후 로그아웃 처리
        String jwtToken = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION).split(" ")[1];

        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<UserToken> optionalUserToken = userTokenRepository.findByUserIdAndUseYn(userId, "Y");

        if(optionalUserToken.isPresent()) {
            if(jwtToken.equals(optionalUserToken.get().getUserAccessToken())) {
                optionalUserToken.get().setUseYn("N");
                optionalUserToken.get().setUpdateDate(currentDate.toString());
            }
        }
    }

    public UserToken getUserTokenInfo(String userId) {

        Optional<UserToken> optionalUserToken = userTokenRepository.findByUserIdAndUseYn(userId, "Y");

        return optionalUserToken.orElse(null);
    }
}
