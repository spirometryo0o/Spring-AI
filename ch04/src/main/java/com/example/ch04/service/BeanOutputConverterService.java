package com.example.ch04.service;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.stereotype.Service;

import com.example.ch04.dto.HotelDTO;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class BeanOutputConverterService {
	
	private ChatClient chatClient;
	
	public BeanOutputConverterService(ChatClient.Builder chatClientBuilder) {
		this.chatClient = chatClientBuilder.build();
	}	
	
	// 교재 p103
	public HotelDTO convertLowLevel(String city) {
		
		// 출력 변환기 생성
		BeanOutputConverter<HotelDTO> beanOutputConverter 
										= new BeanOutputConverter<>(HotelDTO.class);
		
		// 프롬프트 템플릿 생성
		PromptTemplate promptemplate = PromptTemplate.builder()
				.template("{city}에서 유명한 호텔 목록 5개를 출력하세요. 간략한 설명 좀 해줘. {format}")
				.build();
		
		// 프롬프트 생성
		Prompt prompt = promptemplate.create(
					Map.of("city", city, "format", beanOutputConverter.getFormat())
				);
		
		// LLM 요청 및 응답
		String answer = chatClient.prompt(prompt).call().content();
		log.info(answer);
		
		
		// Converter로 List 변환
		HotelDTO hotelDTO = beanOutputConverter.convert(answer);
		log.info(hotelDTO);
		
		return hotelDTO;
	}
	
	// 교재 p104
	public HotelDTO convertHighLevel(String city) {	
		
		HotelDTO hotelDTO = chatClient.prompt()
											.user("%s에서 유명한 호텔 목록 5개 출력하시오.".formatted(city))
											.call()
											.entity(HotelDTO.class);		
				
		return hotelDTO;
	}

}




