package com.example.ch03.controller;

import com.example.ch03.service.AiServiceRoleAssignmentPrompt;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping
public class AiControllerRoleAssignmentPrompt {

    private final AiServiceRoleAssignmentPrompt aiService;

    public AiControllerRoleAssignmentPrompt(AiServiceRoleAssignmentPrompt aiService) {
        this.aiService = aiService;
    }

    @PostMapping(
            value = "/role-assignment",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<String> roleAssignment(@RequestParam String requirements) {
        return aiService.roleAssignment(requirements);
    }
}