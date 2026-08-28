package kr.co.sboard.ch04.service;

import kr.co.sboard.ch04.DTO.HotelDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.cglib.core.Converter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class GenericBeanOutputConverterService {

    private ChatClient chatClient;

    public GenericBeanOutputConverterService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public List<String> convertLowLevel(String city) {

        // 출력 변환기 생성
        BeanOutputConverter <List<HotelDTO>> beanOutputConverter
         = new BeanOutputConverter<>(new ParameterizedTypeReference<List<HotelDTO>>() {});


        // 프롬프트 템플릿 생성
        PromptTemplate promptemplate = PromptTemplate.builder()
                .template("다음 도시들에서 유명한 호텔 3개를 출력하시오. {cities} {format}")
                .build();

        // 프롬프트 생성
        Prompt prompt = PromptTemplate.builder()
                Map.of("city", city, "format", Converter.getFormat())
        );

        // LLM 요청 및 응답
        String answer = chatClient.prompt(prompt).call().content();
        log.info(answer);


        // Converter로 List 변환
        List<String> answerList = converter.convert(answer);
        log.info(answerList);

        return answerList;
    }

    public List<String> convertHighLevel(String city) {

        List<String> answerList = chatClient.prompt()
                .user("%s에서 유명한 호텔 목록 5개 출력하시오.".formatted(city))
                .call()
                .entity(new ListOutputConverter());

        return answerList;
    }

}