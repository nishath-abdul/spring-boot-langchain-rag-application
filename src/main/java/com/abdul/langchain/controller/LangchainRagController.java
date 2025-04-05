package com.abdul.langchain.controller;

import com.abdul.langchain.service.LangchainRagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class LangchainRagController {

    private static final Logger logger = LoggerFactory.getLogger(LangchainRagController.class);

    private final LangchainRagService chatService;

    public LangchainRagController(LangchainRagService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/askQuestion")
    public ResponseEntity<String> askQuestion(@RequestBody Map<String, String> query){
        logger.info("Incoming request query :: {}", query);
        var response = chatService.chat(query);
        return ResponseEntity.ok(response);
    }
}
