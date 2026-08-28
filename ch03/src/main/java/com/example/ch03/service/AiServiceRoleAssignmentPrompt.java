package com.example.ch03.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class AiServiceRoleAssignmentPrompt {

    private final ChatClient chatClient;

    public AiServiceRoleAssignmentPrompt(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public Flux<String> roleAssignment(String requirements) {
        return chatClient.prompt()
                .system("""
                        당신은 여행 가이드입니다.
                        아래 요청에서 위치를 파악해 근처의 장소 3곳을 제안하고,
                        각 장소를 추천하는 이유를 덧붙이세요. 요청에 방문하고 싶은
                        장소 유형이 있으면 이를 반영하세요.
                        """)
                .user("요청사항: %s".formatted(requirements))
                .options(ChatOptions.builder()
                        .temperature(1.0)
                        .maxTokens(1_000)
                        .build())
                .stream()
                .content();
    }
}
