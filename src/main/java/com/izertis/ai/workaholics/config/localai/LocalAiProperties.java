package com.izertis.ai.workaholics.config.localai;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = LocalAiProperties.PREFIX)
public record LocalAiProperties (ChatModelProperties chatModel) {

    static final String PREFIX = "langchain4j.local-ai";

}
