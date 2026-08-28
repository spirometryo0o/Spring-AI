package com.example.ch08.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.ch08.service.TextEmbeddingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Controller
public class TextEmbeddingController {
	
	private final TextEmbeddingService service;
	
	@GetMapping("/ai/text-embedding")
	public String textEmbedding() {
		return "/text-embedding";
	}
	
	@ResponseBody
	@PostMapping("/ai/text-embedding")
	public String textEmbedding(@RequestParam("question") String question) {		
		service.embedding(question);		
		return "결과 로그 확인 하세요.";
	}

}








