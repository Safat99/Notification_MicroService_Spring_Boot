package com.tnpay.notificationmicroservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class Test {

    @GetMapping("/ping")
    String ping() {
        return "pong";
    }
}
