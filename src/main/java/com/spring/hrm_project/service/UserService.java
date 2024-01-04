package com.spring.hrm_project.service;

import com.spring.hrm_project.common.config.domain.dto.LoginRequest;
import com.spring.hrm_project.common.config.domain.entity.User;
import com.spring.hrm_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //final BCryptPasswordEncoder encoder;

    /**
     * 로그인 기능
     * 화면에서 LoginRequest(loginId, password)를 입력받아 loginId와 password가 일치하면 User return
     * loginId가 존재하지 않거나 password가 일치하지 않으면 null return
     */
    public User login(LoginRequest loginRequest) {
        Optional<User> optionalUser = userRepository.findByLoginId(loginRequest.getLoginId());

        // loginId와 일치하는 유저가 없으면 null return
        if(optionalUser.isEmpty()) {
            return null;
        }

        User user = optionalUser.get();

        // 찾아온 유저의 password와 입력된 password가 다르면 null return
        if(!user.getPassword().equals(loginRequest.getPassword())) {
            return null;
        }

        return user;
    }

    /**
     * userId(Long)를 입력받아 User을 return 해주는 기능
     * 인증, 인가 시 사용
     * userId가 null이거나(로그인 X) userId로 찾아온 User가 없으면 null return
     * userId로 찾아온 User가 존재하면 User return
     */
//    public User getLoginUserById(Long userId) {
//        if(userId == null) {
//            return null;
//        }
//
//        Optional<User> optionalUser = userRepository.findById(userId);
//        if(optionalUser.isEmpty()) {
//            return null;
//        }
//
//        return optionalUser.get();
//    }

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

//    /**
//     * loginId 중복 체크
//     * 회원가입 기능 구현 시 사용
//     * 중복되면 true return
//     */
//    public boolean checkLoginIdDuplicate(String loginId) {
//        return userRepository.existByLoginId(loginId);
//    }
//
//    /**
//     * nickname 중복 체크
//     * 회원가입 기능 구현 시 사용
//     * 중복되면 true return
//     */
//    public boolean checkNicknameDuplicate(String nickname) {
//        return userRepository.existByNickname(nickname);
//    }

}
