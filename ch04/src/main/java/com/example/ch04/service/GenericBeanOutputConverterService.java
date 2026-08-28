package com.example.ch04.service;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.example.ch04.dto.HotelDTO;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class GenericBeanOutputConverterService {
	
	private ChatClient chatClient;
	
	public GenericBeanOutputConverterService(ChatClient.Builder chatClientBuilder) {
		this.chatClient = chatClientBuilder.build();
	}	
	
	public List<HotelDTO> convertLowLevel(String cities) {
		
		// 출력 변환기 생성
		BeanOutputConverter<List<HotelDTO>> beanOutputConverter
		 = new BeanOutputConverter<>(new ParameterizedTypeReference<List<HotelDTO>>() {});
		
		// 프롬프트 템플릿 생성
		PromptTemplate promptemplate = PromptTemplate.builder()
				.template("다음 도시들에서 유명한 호텔 3개를 출력하시오. {cities} {format}")
				.build();
		
		// 프롬프트 생성
		Prompt prompt = promptemplate.create(
					Map.of("cities", cities, "format", beanOutputConverter.getFormat())
				);
		
		// LLM 요청 및 응답
		String answer = chatClient.prompt(prompt).call().content();
		log.info(answer);
		
		
		// Converter로 List 변환
		List<HotelDTO> answerList = beanOutputConverter.convert(answer);
		log.info(answerList);
		
		return answerList;
	}
	
	// 교재 p107
	public List<String> convertHighLevel(String city) {	
		
		List<String> answerList = chatClient.prompt()
											.user("%s에서 유명한 호텔 목록 5개 출력하시오.".formatted(city))
											.call()
											.entity(new ListOutputConverter());		
				
		return answerList;
	}

}




