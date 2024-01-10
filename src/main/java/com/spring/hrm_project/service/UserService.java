package com.spring.hrm_project.service;

import com.spring.hrm_project.domain.dto.LoginRequest;
import com.spring.hrm_project.domain.entity.User;
import com.spring.hrm_project.repository.UserRepository;
import com.spring.hrm_project.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 로그인 기능
     * 화면에서 LoginRequest(loginId, password)를 입력받아 loginId와 password가 일치하면 User return
     * loginId가 존재하지 않거나 password가 일치하지 않으면 null return
     */
    public User login(LoginRequest loginRequest) {
        Optional<User> optionalUser = userRepository.findByLoginId(loginRequest.getLoginId());

        // 로그인아이디와 일치하는 유저가 없으면 널값 리턴
        if(optionalUser.isEmpty()) {
            return null;
        }

        User user = optionalUser.get();

        // 찾아온 유저의 패스워드와 입력된 패스워드가 다르면 널값 리턴
        if(!user.getPassword().equals(loginRequest.getPassword())) {
            return null;
        }

        // 로그인 성공 => Jwt Token 발급
        String secretKey = "my-secret-key-123123";
        long expireTimeMs = 1000 * 60 * 60;     // Token 유효 시간 = 60분

        String jwtToken = JwtTokenUtil.createToken(user.getLoginId(), secretKey, expireTimeMs);

        user = User.builder()
                .accessToken(jwtToken)
                .loginId(user.getLoginId())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .userRole(user.getUserRole())
                .id(user.getId())
                .build();
        userRepository.save(user);

        return user;
    }

    /**
     * loginId(String)를 입력받아 User을 return 해주는 기능
     * 인증, 인가 시 사용
     * loginId가 null이거나(로그인 X) userId로 찾아온 User가 없으면 null return
     * loginId로 찾아온 User가 존재하면 User return
     */
    public User getLoginUserByLoginId(String loginId) {
        if(loginId == null) return null;

        Optional<User> optionalUser = userRepository.findByLoginId(loginId);
        if(optionalUser.isEmpty()) return null;

        return optionalUser.get();
    }


    /**
     * 로그아웃 기능
     */
    public void logout() {
        // 로그인한 사용자 정보 가져와서 토큰 삭제 후 로그아웃 처리
        
    }

}
