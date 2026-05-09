package ai.duke.rag;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class DocumentLoader {

    @Inject
    private EmbeddingStore<TextSegment> embeddingStore;

    @Inject
    private EmbeddingModel embeddingModel;

    void loadDocuments(@Observes @Initialized(ApplicationScoped.class) Object pointless) {

        try {

            List<Document> documents = new ArrayList<>();

            String[] files = {
                    "docs/list-of-cars.txt",
                    "docs/list-of-ducks.txt"
            };

            DocumentParser parser = new TextDocumentParser();

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

            for (String file : files) {

                try (InputStream is = classLoader.getResourceAsStream(file)) {

                    if (is == null) {
                        throw new RuntimeException("File not found: " + file);
                    }

                    String text = new BufferedReader(
                            new InputStreamReader(is, StandardCharsets.UTF_8))
                            .lines()
                            .reduce("", (a, b) -> a + "\n" + b);

                    documents.add(Document.from(text));
                }
            }

            EmbeddingStoreIngestor.builder()
                    .embeddingStore(embeddingStore)
                    .embeddingModel(embeddingModel)
                    .documentSplitter(DocumentSplitters.recursive(500, 50))
                    .build()
                    .ingest(documents);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}