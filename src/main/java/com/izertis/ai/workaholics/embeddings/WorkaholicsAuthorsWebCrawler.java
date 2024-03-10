package com.izertis.ai.workaholics.embeddings;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.DocumentTransformer;
import dev.langchain4j.data.document.loader.UrlDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.document.transformer.HtmlTextExtractor;
import dev.langchain4j.model.Tokenizer;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class WorkaholicsAuthorsWebCrawler {

    public static final int MAX_SEGMENT_SIZE = 100;
    public static final int MAX_OVERLAP_SIZE_IN_CHARS = 5;
    private final EmbeddingStoreIngestor ingestor;
    private final DocumentTransformer transformer;


    public WorkaholicsAuthorsWebCrawler(EmbeddingStore authorsEmbeddingStore,
                                        EmbeddingModel embeddingModel,
                                        Optional<Tokenizer> tokenizer) {
        DocumentSplitter splitter = DocumentSplitters.recursive(MAX_SEGMENT_SIZE, MAX_OVERLAP_SIZE_IN_CHARS);
        if (tokenizer.isPresent()){
            splitter = DocumentSplitters.recursive(MAX_SEGMENT_SIZE, MAX_OVERLAP_SIZE_IN_CHARS, tokenizer.get());
        }
        this.transformer = new HtmlTextExtractor("#td-outer-wrap",
                Map.of(),
                true);
        this.ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(splitter)
                .embeddingModel(embeddingModel)
                .embeddingStore(authorsEmbeddingStore)
                .build();
    }

    @PostConstruct
    void init(){
        ingestByUrl("https://www.adictosaltrabajo.com/participa/");
    }

    private void ingestByUrl(String url) {
        log.info("ingesting {} ", url);
        final Document document = UrlDocumentLoader.load(url, new TextDocumentParser());
        final Document documentTransformed = transformer.transform(document);
        documentTransformed.metadata().add("url", url);
        ingestor.ingest(documentTransformed);
    }

}
