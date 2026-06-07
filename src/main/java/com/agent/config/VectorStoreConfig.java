package com.agent.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.pgvector.PgVectorEmbeddingStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@Configuration
public class VectorStoreConfig {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public EmbeddingStore<TextSegment> pgVectorEmbeddingStore() {
        // Strip the "jdbc:" prefix so Java's URI class can parse the connection string
        String cleanUri = datasourceUrl.replace("jdbc:", "");
        URI uri = URI.create(cleanUri);
        
        String host = uri.getHost();
        int port = uri.getPort() == -1 ? 5432 : uri.getPort();
        String database = uri.getPath().replace("/", "");

        System.out.println("====== INITIALIZING PGVECTOR EMBEDDING STORE ON HOST: " + host + " PORT: " + port + " ======");

        return PgVectorEmbeddingStore.builder()
                .host(host)
                .port(port)
                .database(database)
                .user(username)
                .password(password)
                .table("document_embeddings") // The table where vectors will live
                .dimension(3072)               // Critical: Gemini text-embedding-004 uses 768 dimensions
                .createTable(true)            // Framework will auto-generate the table if missing
                .dropTableFirst(false)        // Set to true only if you want to wipe data on restart
                .build();
    }
}
