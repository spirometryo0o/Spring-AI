package com.example.ch04.service;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

import com.example.ch04.dto.HotelDTO;
import com.example.ch04.dto.ReviewClassification;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class SystemMessageService {
	
	private ChatClient chatClient;
	
	public SystemMessageService(ChatClient.Builder chatClientBuilder) {
		this.chatClient = chatClientBuilder.build();
	}	
	
	// 교재 p112
	public ReviewClassification classifyReview(String review) {
		
		
		ReviewClassification reviewClassification = chatClient.prompt()
			.system("""
					영화 리뷰를 [POSITIVE, NEUTRAL, NEGATIVE] 중에서 하나로 분류하고,
					유효한 JSON을 반환하시오.
					""")
			.user("%s".formatted(review))			
			.call()
			.entity(ReviewClassification.class);
		
		
		return reviewClassification;
	}
	

}




