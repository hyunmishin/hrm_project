package com.spring.hrm_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController {

    @GetMapping()
    public ResponseEntity<Void> getMainList(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
