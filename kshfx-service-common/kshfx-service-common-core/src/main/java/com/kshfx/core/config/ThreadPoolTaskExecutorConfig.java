package com.kshfx.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class ThreadPoolTaskExecutorConfig {

    @Bean("taskModuleExecutor")
    ThreadPoolTaskExecutor getCrawler1(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        //设置核心线程数
        threadPoolTaskExecutor.setCorePoolSize(15);
        threadPoolTaskExecutor.setMaxPoolSize(30);
        //设置队列容量,默认为Integer.MAX_VALUE
        threadPoolTaskExecutor.setQueueCapacity(200);
        //允许线程空闲时间60秒
        threadPoolTaskExecutor.setKeepAliveSeconds(60);
        threadPoolTaskExecutor.setThreadNamePrefix("task-concurrent-work");
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}
