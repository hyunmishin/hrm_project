package com.spring.hrm_project.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ScheduleCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;


}
