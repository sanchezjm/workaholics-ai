package com.izertis.ai.workaholics.embeddings;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.DocumentTransformer;
import dev.langchain4j.data.document.Metadata;
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
import java.util.Set;

@Component
@Slf4j
public class WorkaholicsTutorialsWebCrawler {

    public static final int MAX_SEGMENT_SIZE = 100;
    public static final int MAX_OVERLAP_SIZE_IN_CHARS = 5;
    private final EmbeddingStoreIngestor ingestor;
    private final DocumentTransformer transformer;

    private final Set<Metadata> tutorialsMetadataStorage;

    public WorkaholicsTutorialsWebCrawler(EmbeddingStore embeddingStore,
                                          EmbeddingModel embeddingModel,
                                          Optional<Tokenizer> tokenizer,
                                          Set<Metadata> tutorialsMetadataStorage) {
        DocumentSplitter splitter = DocumentSplitters.recursive(MAX_SEGMENT_SIZE, MAX_OVERLAP_SIZE_IN_CHARS);
        if (tokenizer.isPresent()){
            splitter = DocumentSplitters.recursive(MAX_SEGMENT_SIZE, MAX_OVERLAP_SIZE_IN_CHARS, tokenizer.get());
        }
        this.transformer = new HtmlTextExtractor(".td-post-content",
                Map.of("title", "h1.entry-title",
                        "author", ".td-post-author-name",
                        "date", ".td-post-date",
                        "views", ".td-post-views"),
                true);
        this.ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(splitter)
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();
        this.tutorialsMetadataStorage = tutorialsMetadataStorage;
    }

    @PostConstruct
    void init(){
        ingestByUrl("https://www.adictosaltrabajo.com/2023/08/21/devops-eso-es-cosa-del-pasado-conoce-mlops/");
        ingestByUrl("https://www.adictosaltrabajo.com/2023/07/27/nltk-python/");
        ingestByUrl("https://www.adictosaltrabajo.com/2023/05/10/como-ia-puede-mejorar-eficiencia-programador/");
        ingestByUrl("https://www.adictosaltrabajo.com/2023/05/06/diagramas-de-arquitectura-con-c4-model/");
        ingestByUrl("https://www.adictosaltrabajo.com/2019/01/08/haciendo-bdd-en-microservicios-hexagonales-spring-boot/");
        ingestByUrl("https://www.adictosaltrabajo.com/2023/05/12/structurizr-para-generar-diagramas-de-arquitectura-con-c4-model/");
        ingestByUrl("https://www.adictosaltrabajo.com/2020/12/09/ejecucion-de-tareas-efimeras-en-una-arquitectura-de-microservicios-en-cloud/");
    }

    private void ingestByUrl(String url) {
        log.info("ingesting {} ", url);
        final Document document = UrlDocumentLoader.load(url, new TextDocumentParser());
        final Document documentTransformed = transformer.transform(document);
        documentTransformed.metadata().add("url", url);
        ingestor.ingest(documentTransformed);

        tutorialsMetadataStorage.add(documentTransformed.metadata());
    }

}
