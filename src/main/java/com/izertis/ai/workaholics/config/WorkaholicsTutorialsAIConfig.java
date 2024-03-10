package com.izertis.ai.workaholics.config;

import com.izertis.ai.workaholics.WorkaholicsAuthorsAgent;
import com.izertis.ai.workaholics.WorkaholicsTutorialsAgent;
import com.izertis.ai.workaholics.tools.WorkaholicsTools;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.Tokenizer;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiModelName;
import dev.langchain4j.model.openai.OpenAiTokenizer;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.injector.ContentInjector;
import dev.langchain4j.rag.content.injector.DefaultContentInjector;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

@Configuration
public class WorkaholicsTutorialsAIConfig {

    @Bean
    public WorkaholicsTutorialsAgent workaholicsAgent(ChatLanguageModel chatLanguageModel,
                                                      RetrievalAugmentor retrievalAugmentor,
                                                      ContentRetriever contentRetriever,
                                                      WorkaholicsTools workaholicsTools) {
        return AiServices.builder(WorkaholicsTutorialsAgent.class)
                .chatLanguageModel(chatLanguageModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(20))
                // .contentRetriever(contentRetriever)
                .retrievalAugmentor(retrievalAugmentor)
                .tools(workaholicsTools)
                .build();
    }

    @Bean
    public RetrievalAugmentor retrievalAugmentor(ContentRetriever contentRetriever, ContentInjector contentInjector){
        return DefaultRetrievalAugmentor.builder()
                .contentRetriever(contentRetriever)
                .contentInjector(contentInjector)
                .build();
    }
    @Bean
    ContentInjector contentInjector(){
        return DefaultContentInjector.builder()
        .metadataKeysToInclude(Arrays.asList("title", "author", "views", "date", "url"))
        .build();
    }

    @Bean
    ContentRetriever contentRetriever(EmbeddingStore<TextSegment> embeddingStore, EmbeddingModel embeddingModel) {

        // You will need to adjust these parameters to find the optimal setting, which will depend on two main factors:
        // - The nature of your data
        // - The embedding model you are using
        int maxResults = 1;
        double minScore = 0.6;

        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(maxResults)
                .minScore(minScore)
                .build();
    }

    @Bean
    @ConditionalOnMissingBean
    EmbeddingModel embeddingModel() {
        return new AllMiniLmL6V2EmbeddingModel();
    }

    @Bean
    @ConditionalOnProperty("langchain4j.open-ai.chat-model.model-name")
    Tokenizer openAiTokenizer() {
        return new OpenAiTokenizer(OpenAiModelName.GPT_3_5_TURBO);
    }

    @Bean
    EmbeddingStore<TextSegment> embeddingStore() {
        return new InMemoryEmbeddingStore();
    }

    @Bean
    Set<Metadata> tutorialsMetadataStorage() {
       return new HashSet<>();
    }

}
