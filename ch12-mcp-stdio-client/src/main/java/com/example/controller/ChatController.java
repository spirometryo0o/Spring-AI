package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.service.ChatService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Controller
public class ChatController {

	private final ChatService service;
	
	
	@GetMapping("/ai/chat")
	public String chat() {			
		return "/chat";
	}
	
	@ResponseBody
	@PostMapping("/ai/chat")
	public String chat(@RequestParam("question") String question) {
		String answer = service.chat(question);
		return answer;
	}
	
}






