package com.kickmate.kickmate.domain.commentary.ai;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AIClientConfig {

    @Bean
    public WebClient aiWebClient() {
        return WebClient.builder()
                .baseUrl("http://ai-server")  //FastAPI 주소
                .build();
    }
}
