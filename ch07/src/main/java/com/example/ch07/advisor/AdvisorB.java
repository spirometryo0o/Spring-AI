package com.example.ch07.advisor;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import org.springframework.core.Ordered;
import org.springframework.web.reactive.function.client.ClientResponse;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

@Log4j2
public class AdvisorB implements CallAdvisor, StreamAdvisor {
	
	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE + 2;
	}

	@Override
	public Flux<ChatClientResponse> adviseStream(ChatClientRequest chatClientRequest, StreamAdvisorChain streamAdvisorChain) {
		
		log.info("[전처리]");
		
		Flux<ChatClientResponse> response = streamAdvisorChain.nextStream(chatClientRequest);
		
		log.info("[후처리]");
		
		return response;
	}

	@Override
	public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
		
		log.info("[전처리]");
		
		ChatClientResponse response = callAdvisorChain.nextCall(chatClientRequest);
		
		log.info("[후처리]");
		
		return response;
	}
}
