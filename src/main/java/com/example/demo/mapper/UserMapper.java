package com.example.demo.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.User;

/**
 * 用户表 Mapper 接口
 *
 * @author 一陌千尘
 * @date 2025/01/02
 */
@DS("master")
public interface UserMapper extends BaseMapper<User> {

}
