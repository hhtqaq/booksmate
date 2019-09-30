package com.ecjtu.hht.booksmate.ms_oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication(scanBasePackages = "com.ecjtu.hht.booksmate")
@EnableDiscoveryClient
public class MsOauthApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsOauthApplication.class, args);
    }

}
