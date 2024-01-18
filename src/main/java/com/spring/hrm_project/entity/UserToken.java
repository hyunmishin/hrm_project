package com.spring.hrm_project.entity;

import com.spring.hrm_project.entity.idClass.UserTokenId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserTokenId.class)
public class UserToken {

    @Id
    private String userId;

    @Id
    private String userAccessToken;

    @Id
    private String userRefreshToken;

    private String createId;

    private String createDate;

    private String updateId;

    private String updateDate;

    private String useYn;

}
