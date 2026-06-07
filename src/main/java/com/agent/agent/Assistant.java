package com.agent.agent;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;

@AiService
public interface Assistant {
    @SystemMessage({
        "You are an intelligent backend developer assistant.",
        "You have access to tools. Always use the provided tools to calculate sums or products before responding."
    })
    String chat(@MemoryId String memoryId, @UserMessage String userMessage);
}
