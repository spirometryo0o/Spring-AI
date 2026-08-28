package com.example.ch04.service;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class MapOutputConverterService {
	
	private ChatClient chatClient;
	
	public MapOutputConverterService(ChatClient.Builder chatClientBuilder) {
		this.chatClient = chatClientBuilder.build();
	}	
	
	// 교재 p109
	public Map<String, Object> convertLowLevel(String hotel) {
		
		// 구조화된 출력 변환기 생성
	    MapOutputConverter mapOutputConverter = new MapOutputConverter();
	    
	    // 프롬프트 템플릿 생성
	    PromptTemplate promptTemplate = new PromptTemplate(
	        "호텔 {hotel}에 대해 정보를 알려주세요 {format}");
	    
	    // 프롬프트 생성
	    Prompt prompt = promptTemplate.create(Map.of(
	        "hotel", hotel,
	        "format", mapOutputConverter.getFormat()));
	    	    
	    // LLM의 JSON 출력 얻기
	    String json = chatClient.prompt(prompt)
	        .call()
	        .content();
	    
	    // List<String>으로 변환
	    Map<String, Object> hotelInfo = mapOutputConverter.convert(json);
	    
	    return hotelInfo;
	}
	
	// 교재 p109
	public Map<String, Object> convertHighLevel(String hotel) {	
		
		Map<String, Object> hotelInfo = chatClient.prompt()
										        .user("호텔 %s에 대해 정보를 알려주세요".formatted(hotel))
										        .call()
										        .entity(new MapOutputConverter());
				
		return hotelInfo;
	}

}




