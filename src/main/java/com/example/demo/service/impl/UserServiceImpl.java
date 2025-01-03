package com.example.demo.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Order;
import com.example.demo.entity.User;
import com.example.demo.mapper.Order2Mapper;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * 用户服务实现类
 *
 * @author 一陌千尘
 * @date 2025/01/02
 */
@DS("master") // 指定数据源，不指定则使用默认数据源 master
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    // 使用 master 数据源
    private final UserMapper userMapper;
    // 使用 slave1 数据源
    private final OrderMapper orderMapper;
    // 未指定数据源
    private final Order2Mapper order2Mapper;

    @Override
    public String test1() {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.lambdaQuery(User.class).eq(User::getName, "aaa");
        User user = userMapper.selectOne(userLambdaQueryWrapper);
        log.info("test1 ===== user: {}", user.getName());
        LambdaQueryWrapper<Order> orderLambdaQueryWrapper = Wrappers.lambdaQuery(Order.class).eq(Order::getCode, "bbb");
        Order order = orderMapper.selectOne(orderLambdaQueryWrapper);
        log.info("test1 ===== order: {}", order.getCode());
        return "ok";
    }

    @Override
    @DS("slave1") // 指定数据源，不指定则使用类上指定的数据源，如果类上未指定则使用默认数据源 master
    public String test2() {
        LambdaQueryWrapper<Order> userLambdaQueryWrapper = Wrappers.lambdaQuery(Order.class).eq(Order::getCode, "bbb");
        Order order = order2Mapper.selectOne(userLambdaQueryWrapper);
        log.info("test2 ===== order: {}", order.getCode());
        return "ok";
    }

}
