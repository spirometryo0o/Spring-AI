package com.example.ch08.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.ch08.service.AddDocumentService;
import com.example.ch08.service.TextEmbeddingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Controller
public class AddDocumentController {
	
	private final AddDocumentService service;
	
	@GetMapping("/ai/add-document")
	public String addDocument() {
		return "/add-document";
	}
	
	@ResponseBody
	@PostMapping("/ai/add-document")
	public String addDocument(@RequestParam("question") String question) {
		
		//service.addDocument();
		service.addDocumentShopping();
		return "벡터 저장소를 확인하세요.";
	}
	

}








