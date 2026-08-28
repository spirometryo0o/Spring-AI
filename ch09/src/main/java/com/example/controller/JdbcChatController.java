package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.service.InMemoryChatService;
import com.example.service.JdbcChatService;
import com.example.service.VectorStoreChatService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Controller
public class JdbcChatController {
	
	private final JdbcChatService service;

	@GetMapping("/ai/jdbc-chat")
	public String jdbcChat() {
		return "/jdbc-chat";
	}
	
	@ResponseBody
	@PostMapping("/ai/jdbc-chat")
	public String vectorStoreChat(@RequestParam("question") String question, HttpSession session) {
		
		String sessionId = session.getId();
		
		String answer = service.chat(question, sessionId);
		
		return answer;
	}

}







