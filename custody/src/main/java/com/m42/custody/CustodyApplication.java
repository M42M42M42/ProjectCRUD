package com.m42.custody;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.m42.custody.mapper")
public class CustodyApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustodyApplication.class, args);
    }

}
