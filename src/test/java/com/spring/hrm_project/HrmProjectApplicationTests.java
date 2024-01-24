package com.spring.hrm_project;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.hrm_project.model.security.SecurityApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.spring.hrm_project.entity.QApi.api;
import static com.spring.hrm_project.entity.QApiRole.apiRole;
import static com.spring.hrm_project.entity.QRole.role;

@SpringBootTest
class HrmProjectApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	@Test
	public void getSecurityApi(){
		List<SecurityApi> result =  jpaQueryFactory
				.from(api)
				.leftJoin(apiRole).on(apiRole.apiId.eq(api.apiId))
				.leftJoin(role).on(role.roleId.eq(apiRole.roleId))
				.where(role.roleId.isNotEmpty())
				.orderBy(api.apiUrl.asc())
				.transform(
						groupBy(api.apiUrl).list(
								Projections.fields(
										SecurityApi.class,
										api.apiUrl,
										list(role.roleId).as("roleList")
								)
						)
				);

		for(SecurityApi securityApi: result){
			System.out.println(securityApi);
		}
	}

}
