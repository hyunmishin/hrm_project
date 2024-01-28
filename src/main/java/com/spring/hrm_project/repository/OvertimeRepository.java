package com.spring.hrm_project.repository;

import com.spring.hrm_project.entity.Overtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OvertimeRepository extends JpaRepository<Overtime, String> {2
}
