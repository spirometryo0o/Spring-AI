package com.example.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ChatService {
	
	private ChatClient chatClient;
	
	public ChatService(ChatClient.Builder chatClientBuilder, 
					   ToolCallbackProvider toolCallbackProvider) {
		
		this.chatClient = chatClientBuilder
							.defaultTools(toolCallbackProvider) // 도구 호출 정보를 제공하는 tool
							.build();
	}
	
	
	public String chat(String question) {
		
		String answer = chatClient
							.prompt()
							.system("현재 날짜와 시간 질문은 반드시 도구를 사용하세요.")							
							.user(question)
							.call()
							.content();
		
		return answer;
	}
	

}





