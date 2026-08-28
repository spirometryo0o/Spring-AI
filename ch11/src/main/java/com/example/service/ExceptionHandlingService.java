package com.example.service;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import com.example.tool.DateTimeTools;
import com.example.tool.ExceptionHandlingTools;
import com.example.tool.HeatingSystemTools;
import com.example.tool.RecommendMovieTools;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ExceptionHandlingService {

	private ChatClient chatClient;
	private ExceptionHandlingTools exceptionHandlingTools;
	
	public ExceptionHandlingService(ChatClient.Builder chatClientBuilder, ExceptionHandlingTools exceptionHandlingTools) {
		this.chatClient = chatClientBuilder.build();
		this.exceptionHandlingTools = exceptionHandlingTools;
	}
	
	public String chat(String question) {
		
		String answer = this.chatClient								
								.prompt()								
								.user("""
									질문에 대해 답변해 주세요.
									사용자 ID가 존재하지 않을 경우, 진행을 멈추고,
									'[LLM] 질문을 처리할 수 없습니다.'라고 답변해 주세요.
									
									질문: %s""".formatted(question))
								.tools(exceptionHandlingTools)
								.call()
								.content();
		return answer;
	}
	
}





