package com.room.ShangTingRoom.web.admin.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
// 明确指定Mapper接口的包路径，不包括service包
@MapperScan("com.room.ShangTingRoom.web.admin.mapper")
public class MybatisConfig {
    // 配置类可以为空，主要是使用@MapperScan注解
}
