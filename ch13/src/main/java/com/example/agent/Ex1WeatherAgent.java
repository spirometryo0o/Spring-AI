package com.example.agent;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class Ex1WeatherAgent {

	// 교재 p438 ~ p439
	private static final String SYSTEM_PROMPT = """
			  당신은 날씨 정보를 제공하는 전문 에이전트입니다.
			  날씨 정보가 필요하면 반드시 Tool을 사용해 조회하세요.
			  추측으로 답변하지 마세요.

			  ## 사용 가능한 Tool
			  1. getWeather: 특정 도시의 현재 날씨 정보를 조회
			""";

	private final ChatClient chatClient;

	public Ex1WeatherAgent(ChatClient.Builder builder) {
		this.chatClient = builder
							.defaultSystem(SYSTEM_PROMPT)
							.build();
	}

	public String execute(String userQuery) {
		
		String answer = chatClient
							.prompt()
							.user(userQuery)
							.tools(this)
							.call()
							.content();
		
		return answer;
	}

	@Tool(description = "특정 도시의 현재 날씨 정보를 조회합니다")
	public String getWeather(@ToolParam(description = "도시 이름") String city) {
		return String.format("%s의 현재 날씨는 맑고 23도입니다.", city);
	}
}
