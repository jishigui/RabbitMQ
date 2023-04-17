package com.yix.controller;

import com.yix.config.RabbitConfig;
import com.yix.feignClient.*;
import com.yix.util.GlobalCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@Slf4j
public class PlaceOrderController {

    @Autowired
    private ItemStockClient itemStockClient;

    @Autowired
    private OrderManageClient orderManageClient;

    @Autowired
    private CouponClient couponClient;

    @Autowired
    private UserPointsClient userPointsClient;

    @Autowired
    private BusinessClient businessClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 模拟用户下单
     */
    @GetMapping("/po")
    public String po() {

        long start = System.currentTimeMillis();
        // 1.调用库存服务扣除商品库存
        itemStockClient.decr();
        // 2.调用订单服务，创建订单
        orderManageClient.create();

        String id = UUID.randomUUID().toString();
        // 封装消息的全部信息
        String userAndOrderInfo = "用户信息&订单信息&优惠券信息等等....";
        Map map = new HashMap<>();
        map.put("message",userAndOrderInfo);
        map.put("exchange",RabbitConfig.PLACE_ORDER_EXCHANGE);
        map.put("routingKey","");
        map.put("sendTime",new Date());
        // 将id标识和消息存储到全局缓存中
        GlobalCache.set(id,map);
        rabbitTemplate.convertAndSend(RabbitConfig.PLACE_ORDER_EXCHANGE,"",userAndOrderInfo,new CorrelationData(id));

        // 3.调用优惠券服务，预扣除使用的优惠券
//        couponClient.coupon();
        // 4.调用用户积分服务，预扣除用户使用的积分
//        userPointsClient.userPoint();
        // 5.调用商家服务，通知商家用户已下单
//        businessClient.business();

        System.out.println("服务调用时间" + (System.currentTimeMillis() - start));
        return "placeorder is OK!";
    }
}
