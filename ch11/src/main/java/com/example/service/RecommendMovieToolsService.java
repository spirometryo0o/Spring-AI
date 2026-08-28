package com.example.service;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import com.example.tool.DateTimeTools;
import com.example.tool.HeatingSystemTools;
import com.example.tool.RecommendMovieTools;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class RecommendMovieToolsService {

	private ChatClient chatClient;
	private RecommendMovieTools recommendMovieTools;
	
	public RecommendMovieToolsService(ChatClient.Builder chatClientBuilder, RecommendMovieTools recommendMovieTools) {
		this.chatClient = chatClientBuilder.build();
		this.recommendMovieTools = recommendMovieTools;
	}
	
	public String chat(String question) {
		
		String answer = this.chatClient								
								.prompt()								
								.user(question)
								.tools(recommendMovieTools)
								.call()
								.content();
		return answer;
	}
	
}





