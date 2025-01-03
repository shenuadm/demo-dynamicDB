package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动类
 *
 * @author 一陌千尘
 * @date 2025/01/03
 */
@Slf4j
@SpringBootApplication
@MapperScan({"com.example.demo.mapper"}) // 扫描 mapper 接口
public class DemoDynamicDatasourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoDynamicDatasourceApplication.class, args);
        log.info("起来了！");
    }

}
