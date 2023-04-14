package com.yix.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yix.domain.UserPointsIdempotent;
import com.yix.service.UserPointsIdempotentService;
import com.yix.mapper.UserPointsIdempotentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author Administrator
* @description 针对表【user_points_idempotent】的数据库操作Service实现
* @createDate 2023-04-14 08:42:47
*/
@Service
@Slf4j
public class UserPointsIdempotentServiceImpl extends ServiceImpl<UserPointsIdempotentMapper, UserPointsIdempotent>
    implements UserPointsIdempotentService{

    @Resource
    private UserPointsIdempotentMapper mapper;

    @Override
    @Transactional
    public void consume(Message message) {
        String id = message.getMessageProperties().getCorrelationId();
        // 1.查询幂等表是否存在当前消息标识
        LambdaQueryWrapper<UserPointsIdempotent> wrapper = Wrappers.lambdaQuery();
        int count = mapper.selectCount(wrapper.eq(UserPointsIdempotent::getId,id));
        // 2.如果存在，直接return
        if (count == 1) {
            log.info("消息已被消费！！！无需重复消费！");
            return;
        }
        // 3.如果不存在，插入消息表示到幂等表
        UserPointsIdempotent idempotent = new UserPointsIdempotent();
        idempotent.setId(id);
        mapper.insert(idempotent);
        // 4.执行消费逻辑
        // 预扣除用户积分
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("扣除用户积分成功！！");
    }
}




