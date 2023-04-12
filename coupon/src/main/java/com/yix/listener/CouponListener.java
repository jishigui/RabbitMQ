package com.yix.listener;

import com.rabbitmq.client.Channel;
import com.yix.config.RabbitConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CouponListener {
    @RabbitListener(queues = {RabbitConfig.CONPON_QUEUE})
    public void consume(String msg, Channel channel, Message message) {
        // 预扣除优惠券
        try {
            Thread.sleep(400);
            System.out.println("优惠券扣除成功!" + msg);
            // 手动ACK
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
