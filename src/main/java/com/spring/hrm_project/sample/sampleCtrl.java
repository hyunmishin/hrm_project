package com.spring.hrm_project.sample;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sample")
public class sampleCtrl {

    @GetMapping
    public ResponseEntity<String> sample(){
        return new ResponseEntity<>("신바", HttpStatus.OK);
    }

}
