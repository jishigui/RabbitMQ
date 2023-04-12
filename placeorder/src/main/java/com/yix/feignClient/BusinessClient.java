package com.yix.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "business")
public interface BusinessClient {
    @GetMapping("bus")
    public void business();
}
