package com.agent.service;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;

@Component
public class IngestionService implements CommandLineRunner {

    private final EmbeddingStore<TextSegment> embeddingStore;
    private final EmbeddingModel embeddingModel;

    public IngestionService(EmbeddingStore<TextSegment> embeddingStore, EmbeddingModel embeddingModel) {
        this.embeddingStore = embeddingStore;
        this.embeddingModel = embeddingModel;
    }

    @Override
    public void run(String... args) throws Exception {
        // Load knowledge documents from src/main/resources/knowledge.txt
        ClassPathResource resource = new ClassPathResource("knowledge.txt");
        if (!resource.exists()) {
            System.out.println("No knowledge repository file found. Skipping RAG ingestion.");
            return;
        }

        Document document = FileSystemDocumentLoader.loadDocument(resource.getFile().toPath(), new TextDocumentParser());

        // Segment, vectorise, and ingest documents into the bean storage instance
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(DocumentSplitters.recursive(300, 30)) // 300 token chunks
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();

        System.out.println("Beginning semantic index ingestion pipeline...");
        ingestor.ingest(document);
        System.out.println("RAG Knowledge base ready for real-time contextual queries.");
    }
}