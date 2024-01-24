package com.spring.hrm_project.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtil {

    public static LocalDateTime getCurrentDate(){
        return LocalDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Seoul"));
    }

}
