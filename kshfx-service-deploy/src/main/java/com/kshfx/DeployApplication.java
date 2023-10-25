package com.kshfx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}, scanBasePackages = {"com.kshfx"})
@MapperScan({"com.kshfx.**.dao"})
@EnableCaching
public class DeployApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeployApplication.class, args);
    }
}