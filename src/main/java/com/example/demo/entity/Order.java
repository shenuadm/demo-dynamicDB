package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 订单实体类
 *
 * @author 一陌千尘
 * @date 2025/01/02
 */
@TableName("`order`")
@Data
public class Order {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String code;
}
