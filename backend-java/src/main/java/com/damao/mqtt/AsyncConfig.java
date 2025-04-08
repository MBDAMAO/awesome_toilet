package com.damao.mqtt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "mqttExecutor")
    public Executor mqttExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);       // 可配置
        executor.setMaxPoolSize(10);       // 可配置
        executor.setQueueCapacity(100);    // 可配置
        executor.setThreadNamePrefix("mqtt-handler-");
        executor.initialize();
        return executor;
    }
}
