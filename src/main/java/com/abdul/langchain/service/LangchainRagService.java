package com.abdul.langchain.service;

import dev.langchain4j.chain.ConversationalRetrievalChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LangchainRagService {

    private static final Logger logger = LoggerFactory.getLogger(LangchainRagService.class);

    private final ConversationalRetrievalChain chain;

    public LangchainRagService(ConversationalRetrievalChain chain) {
        this.chain = chain;
    }


    public String chat(Map<String, String> query){
      String response =  chain.execute(query.get("requestQuery"));
      logger.info("The Response is :: {}", response);
      return response;
    }
}
