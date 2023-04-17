package com.yix.config;


import cn.hutool.core.bean.BeanUtil;
import com.rabbitmq.client.ReturnCallback;
import com.yix.domain.Resend;
import com.yix.mapper.ResendMapper;
import com.yix.util.GlobalCache;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitTemplateConfig {

    @Autowired
    private ResendMapper resendMapper;

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        // 1.new 出RabbitTemplate对象
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        // 2. 将connectionFactory设置到RabbitTemplate对象中
        rabbitTemplate.setConnectionFactory(connectionFactory);
        // 3.设置confirm回调
        rabbitTemplate.setConfirmCallback(confirmCallback());
        // 4.设置return回调
        rabbitTemplate.setReturnCallback(returnCallback());
        // 5.设置mandatory为true
        rabbitTemplate.setMandatory(true);
        //6. 返回RabbitTemplate对象
        return rabbitTemplate;
    }

    public RabbitTemplate.ConfirmCallback confirmCallback() {
        return new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if (correlationData == null) {
                    String msgId = correlationData.getId();
                }
                String msgId = correlationData.getId();
                if (ack) {
                    System.out.println("消息发送到Exchange成功=" + msgId);
                    GlobalCache.remove(msgId);
                } else {
                    System.out.println("消息发送到Exchange失败=" + msgId);
                    Resend resend = BeanUtil.toBean(GlobalCache.get(msgId),Resend.class);
                    resendMapper.insert(resend);
                }
            }
        };
    }

    public RabbitTemplate.ReturnCallback returnCallback() {
        return new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int i, String replyTest, String exchange, String routingKey) {
                System.out.println("消息为路由到队列");
                System.out.println("消息为：" + new String(message.getBody()));
                System.out.println("交换机为:" + exchange);
                System.out.println("路由为:" + routingKey);
            }
        };
    }
}
