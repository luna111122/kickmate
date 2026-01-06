package com.kickmate.kickmate.domain.commentary.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AIAsyncExecutorConfig {

    @Bean(name = "aiWebhookExecutor")
    public Executor aiWebhookExecutor() {
        ThreadPoolTaskExecutor exec = new ThreadPoolTaskExecutor();
        exec.setCorePoolSize(4);
        exec.setMaxPoolSize(8);
        exec.setQueueCapacity(200);
        exec.setThreadNamePrefix("ai-webhook-");
        exec.initialize();
        return exec;
    }
}