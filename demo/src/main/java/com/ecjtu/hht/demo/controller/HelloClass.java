package com.ecjtu.hht.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hht
 * @date 2019/9/29 17:35
 */
@RestController
public class HelloClass {
    @GetMapping("/hello")
    public String hello(){
        return "nihao zheshishui";
    }
}
