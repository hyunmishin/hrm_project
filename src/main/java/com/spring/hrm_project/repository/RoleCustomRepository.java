package com.spring.hrm_project.repository;


import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.hrm_project.model.security.SecurityApiDto;
import com.spring.hrm_project.model.security.SecurityRoleApi;
import com.spring.hrm_project.model.security.SecurityRoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.spring.hrm_project.entity.QApi.api;
import static com.spring.hrm_project.entity.QApiRole.apiRole;
import static com.spring.hrm_project.entity.QRole.role;

@Repository
@RequiredArgsConstructor
public class RoleCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<SecurityRoleDto> getSecurityRole(){
        return
                jpaQueryFactory
                .from(role)
                .leftJoin(apiRole).on(role.roleId.eq(apiRole.roleId))
                .leftJoin(api).on(api.apiId.eq(apiRole.apiId))
                .transform(
                        groupBy(role.roleId).list(
                                Projections.fields(
                                        SecurityRoleDto.class,
                                        role.roleId,
                                        list(Projections.fields(
                                                SecurityApiDto.class,
                                                api.apiUrl
                                            )
                                        ).as("apiDtoList")
                                )
                        )
                );
    }

    public List<SecurityRoleApi> getSecurityRoleApi(){
        return jpaQueryFactory
                .select(Projections.fields(
                        SecurityRoleApi.class,
                        role.roleId
                        ,api.apiUrl
                )).from(role)
                .leftJoin(apiRole).on(role.roleId.eq(apiRole.roleId))
                .leftJoin(api).on(api.apiId.eq(apiRole.apiId))
                .fetch();
    }

}
