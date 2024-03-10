package com.izertis.ai.workaholics.config.localai;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.localai.LocalAiChatModel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

// @Configuration
// @EnableConfigurationProperties(LocalAiProperties.class)
public class LocalAiConfig {

    @Bean
    @ConditionalOnProperty("langchain4j.local-ai.chat-model.base-url")
    ChatLanguageModel openAiChatModel(LocalAiProperties properties) {
        final ChatModelProperties chatModelProperties = properties.getChatModel();
        return LocalAiChatModel.builder()
                .baseUrl(chatModelProperties.getBaseUrl())
                .modelName(chatModelProperties.getModelName())
                .temperature(chatModelProperties.getTemperature())
                .logRequests(true)
                .logResponses(true)
                .build();
    }

}
