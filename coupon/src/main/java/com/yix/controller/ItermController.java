package com.yix.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItermController {

    @GetMapping("/coupon")
    public void coupon() {
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("优惠券扣除成功!");
    }
}
