package com.agent.controller;

import com.agent.agent.Assistant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final Assistant assistant;

    public ChatController(Assistant assistant) {
        this.assistant = assistant;
    }

    @GetMapping("/api/chat")
    public String chat(@RequestParam String message, 
                       @RequestParam(required = false, defaultValue = "default-session") String sessionId) {
        return assistant.chat(sessionId, message);
    }
}
