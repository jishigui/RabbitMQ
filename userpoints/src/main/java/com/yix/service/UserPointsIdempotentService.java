package com.yix.service;

import com.yix.domain.UserPointsIdempotent;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yix.mapper.UserPointsIdempotentMapper;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;

/**
* @author Administrator
* @description 针对表【user_points_idempotent】的数据库操作Service
* @createDate 2023-04-14 08:42:47
*/
public interface UserPointsIdempotentService extends IService<UserPointsIdempotent> {

    void consume(Message message);

}
