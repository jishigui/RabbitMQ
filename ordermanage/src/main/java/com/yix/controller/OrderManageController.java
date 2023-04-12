package com.yix.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderManageController {

    @GetMapping("/create")
    public void create() {
        System.out.println("创建订单成功!");
    }

}
