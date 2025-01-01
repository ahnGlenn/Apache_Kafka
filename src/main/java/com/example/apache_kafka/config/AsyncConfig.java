package com.example.apache_kafka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;

/*
*  스레드 풀 설정
*  설명 : 멀티쓰레드를 구성하여, 단순 스케줄러 포문의 효율을 높이기 위함
*/
@Configuration
public class AsyncConfig {
    @Bean(name = "kafkaTaskExecutor")
    public Executor kafkaTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // 스레드 풀 크기
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("KafkaSender-");
        executor.initialize();
        return executor;
    }
}