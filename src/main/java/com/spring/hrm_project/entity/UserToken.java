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

@Entity(name = "TB_USER_TOKEN")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserTokenId.class)
public class UserToken {

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Id
    @Column(name = "USER_ACCESS_TOKEN")
    private String userAccessToken;

    @Id
    @Column(name = "USER_REFRESH_TOKEN")
    private String userRefreshToken;

    @Column(name = "CREATE_ID")
    private String createId;

    @Column(name = "CREATE_DATE")
    private String createDate;

    @Column(name = "UPDATE_ID")
    private String updateId;

    @Column(name = "UPDATE_DATE")
    private String updateDate;

    @Column(name = "USE_YN")
    private String useYn;

}
