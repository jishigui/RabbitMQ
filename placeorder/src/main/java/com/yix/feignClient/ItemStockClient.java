package com.yix.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "itermstock")
public interface ItemStockClient {

    @GetMapping("/decr")
    public void decr();
}
