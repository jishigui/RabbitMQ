package com.yix.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BusinessController {

    @GetMapping("bus")
    public void business() {
        System.out.println("business is OK!");
    }
}
