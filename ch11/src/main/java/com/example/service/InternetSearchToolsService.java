package com.example.service;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.content.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;

import com.example.tool.BoomBarrierTools;
import com.example.tool.CarCheckTools;
import com.example.tool.DateTimeTools;
import com.example.tool.FileSystemTools;
import com.example.tool.HeatingSystemTools;
import com.example.tool.InternetSearchTools;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class InternetSearchToolsService {

	private ChatClient chatClient;
	
	@Autowired
	private InternetSearchTools internetSearchTools; 
		
	public InternetSearchToolsService(ChatClient.Builder chatClientBuilder) {
		this.chatClient = chatClientBuilder.build();
	}

	public String chat(String question) {
		
		// LLM으로 요청하고 응답받기
		String answer = chatClient
						.prompt()						
						.user(question)
						.tools(internetSearchTools)
						.call()
						.content();
		return answer;
	}

}
