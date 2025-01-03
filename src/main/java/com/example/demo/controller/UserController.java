package com.example.demo.controller;

import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 用户控制器类
 *
 * @author 一陌千尘
 * @date 2025/01/02
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("test1")
    public String test1() {
        return service.test1();
    }

    @GetMapping("test2")
    public String test2() {
        return service.test2();
    }
}
