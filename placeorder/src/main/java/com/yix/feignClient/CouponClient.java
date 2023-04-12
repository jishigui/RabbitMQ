package com.yix.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "coupon")
public interface CouponClient {
    @GetMapping("/coupon")
    public void coupon();
}
