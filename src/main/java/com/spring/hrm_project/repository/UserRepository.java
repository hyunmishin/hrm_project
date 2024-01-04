package com.spring.hrm_project.repository;

import com.spring.hrm_project.common.config.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

//    public boolean existByLoginId(String loginId);
//    public boolean existByNickname(String nickname);

    Optional<User> findByLoginId(String loginId);

}
