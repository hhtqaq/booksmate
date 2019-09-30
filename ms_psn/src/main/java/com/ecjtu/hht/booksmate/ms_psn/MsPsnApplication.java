package com.ecjtu.hht.booksmate.ms_psn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.ecjtu.hht.booksmate.*.mapper*")
public class MsPsnApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsPsnApplication.class, args);
    }

}
