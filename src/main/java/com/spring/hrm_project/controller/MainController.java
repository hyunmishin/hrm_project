package com.spring.hrm_project.controller;

import com.spring.hrm_project.model.SecurityRoleApi;
import com.spring.hrm_project.model.SecurityRoleDto;
import com.spring.hrm_project.repository.RoleCustomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
@Slf4j
public class MainController {

    private final RoleCustomRepository roleCustomRepository;
    @GetMapping()
    public ResponseEntity<List<SecurityRoleApi>> getMainList(){
        List<SecurityRoleApi> result = roleCustomRepository.getSecurityRoleApi();
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

}
