package com.yix.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "userpoints")
public interface UserPointsClient {

    @GetMapping("/userpoint")
    public void userPoint();
}
