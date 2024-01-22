package com.spring.hrm_project.repository;

import com.spring.hrm_project.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, String> {

    Optional<UserToken> findByUserIdAndUseYn(String userId, String useYn);

}
