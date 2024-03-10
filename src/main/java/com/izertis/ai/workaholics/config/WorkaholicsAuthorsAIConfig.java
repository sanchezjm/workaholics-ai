package com.izertis.ai.workaholics.config;

import com.izertis.ai.workaholics.WorkaholicsAuthorsAgent;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WorkaholicsAuthorsAIConfig {

    @Bean
    public WorkaholicsAuthorsAgent workaholicsAuthorsAgent(ChatLanguageModel chatLanguageModel,
                                                              ContentRetriever authorsContentRetriever) {
        return AiServices.builder(WorkaholicsAuthorsAgent.class)
                .chatLanguageModel(chatLanguageModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(20))
                .contentRetriever(authorsContentRetriever)
                .build();
    }

    @Bean
    ContentRetriever authorsContentRetriever(EmbeddingStore<TextSegment> authorsEmbeddingStore, EmbeddingModel embeddingModel) {

        // You will need to adjust these parameters to find the optimal setting, which will depend on two main factors:
        // - The nature of your data
        // - The embedding model you are using
        int maxResults = 1;
        double minScore = 0.6;

        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(authorsEmbeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(maxResults)
                .minScore(minScore)
                .build();
    }

    @Bean
    EmbeddingStore<TextSegment> authorsEmbeddingStore() {
        return new InMemoryEmbeddingStore();
    }

}
