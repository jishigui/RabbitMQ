package com.yix.controller;

import com.yix.config.RabbitConfig;
import com.yix.feignClient.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
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

        String userAndOrderInfo = "用户信息&订单信息&优惠券信息等等....";
        rabbitTemplate.convertAndSend(RabbitConfig.PLACE_ORDER_EXCHANGE,"",userAndOrderInfo);

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
