package com.yix.listener;

import com.rabbitmq.client.Channel;
import com.yix.config.RabbitConfig;
import com.yix.service.UserPointsIdempotentService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserPointListener {

    @Autowired
    private UserPointsIdempotentService userPointsIdempotentService;

    @RabbitListener(queues = {RabbitConfig.USER_POINTS_QUEUE})
    public void consume(String msg, Channel channel, Message message) {
        // 消费消息
        userPointsIdempotentService.consume(message);
        // 手动ack

    }
}
