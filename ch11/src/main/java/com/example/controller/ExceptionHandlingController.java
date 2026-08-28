package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.service.DateTimeToolsService;
import com.example.service.ExceptionHandlingService;
import com.example.service.HeatingSystemToolsService;
import com.example.service.RecommendMovieToolsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Controller
public class ExceptionHandlingController {
	
	private final ExceptionHandlingService service;
	
	@GetMapping("/ai/exception-handling")
	public String exceptionHandling() {
		return "/exception-handling";
	}	
	
	@ResponseBody
	@PostMapping("/ai/exception-handling")
	public String exceptionHandling(@RequestParam("question") String question) {
		
		try {
			String answer = service.chat(question);
			return answer;
		}catch (Exception e) {
			return "[애플리케이션] 질문을 처리할 수 없습니다. %s".formatted(e.getMessage());
		}
	}
	
}








