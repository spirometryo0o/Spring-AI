package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.service.InMemoryChatService;
import com.example.service.VectorStoreChatService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Controller
public class VectorStoreChatController {
	
	private final VectorStoreChatService service;

	@GetMapping("/ai/vector-store-chat")
	public String vectorStoreChat() {
		return "/vector-store-chat";
	}
	
	@ResponseBody
	@PostMapping("/ai/vector-store-chat")
	public String vectorStoreChat(@RequestParam("question") String question, HttpSession session) {
		
		String sessionId = session.getId();
		
		String answer = service.chat(question, sessionId);
		
		return answer;
	}

}







