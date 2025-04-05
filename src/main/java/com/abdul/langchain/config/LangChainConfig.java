package com.abdul.langchain.config;

import dev.langchain4j.chain.ConversationalRetrievalChain;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;

@Configuration
public class LangChainConfig {

   @Value("${openapi.api.key}")
   private String apikey;

   @Value("${langchain.timeout}")
   private Long timeout;

   private List<Document> documents;


   @Bean
   public ConversationalRetrievalChain chain() {

      EmbeddingModel embeddingModel = new AllMiniLmL6V2EmbeddingModel();
      EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

     /* EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
              .documentSplitter(DocumentSplitters.recursive(500, 0))
              .embeddingModel(embeddingModel)
              .embeddingStore(embeddingStore)
              .build();*/

     // ingestor.ingest(documents);

      EmbeddingStoreContentRetriever retriever = EmbeddingStoreContentRetriever.from(embeddingStore);

      ConversationalRetrievalChain chain = ConversationalRetrievalChain.builder()
              .chatLanguageModel(OpenAiChatModel.builder()
                      .apiKey(apikey)
                      .timeout(Duration.ofSeconds(timeout))
                      .build()
              ).contentRetriever(retriever)
              .build();

      return chain;
   }

}
