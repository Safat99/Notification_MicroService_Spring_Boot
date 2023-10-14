package com.tnpay.notificationmicroservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {


    /**
     *
     * If we not configure this executor, it will create SimpleAsyncTaskExecutor instead
     * of the ThreadPoolTaskExecutor
     * @return ThreadPoolTaskExecutor
     */
    @Bean
    public ThreadPoolTaskExecutor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // thread capacity initialized 5 threads
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(20); // at a time 20 tasks can be waited in the blocking queue
        executor.setThreadNamePrefix("AsyncEmail-"); // we can get to know which thread is active now
        executor.initialize();
        return executor;
    }
}