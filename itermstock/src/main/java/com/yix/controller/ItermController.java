package com.yix.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.RuntimeErrorException;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class ItermController {

    private static AtomicInteger stock = new AtomicInteger(10);;

    @GetMapping("/decr")
    public void decr() {
        if (stock.incrementAndGet()<0) {
            throw new RuntimeException("商品库存不足!");
        }
        System.out.println("扣减库存成功!");
    }
}
