package com.ecjtu.hht.booksmate.ms_psn.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hht
 * @date 2019/9/20 15:30
 */


@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "hello hht";
    }
}
