package com.yix.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserPointsController {

    @GetMapping("/userpoint")
    public void userPoint() {
        System.out.println("userPoint i OK!");
    }
}
