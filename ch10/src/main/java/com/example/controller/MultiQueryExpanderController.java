package com.example.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.service.CompressionQueryTransformerService;
import com.example.service.RagService;
import com.example.service.TranslationQueryTransformerService;
import com.example.service.TxtPdfWordEtlService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Controller
public class MultiQueryExpanderController {

	private final TranslationQueryTransformerService service;
	
	
	@GetMapping("/ai/multi-query-expander")
	public String multiQueryExpander() {		
		return "/multi-query-expander";				
	}
		
	@ResponseBody
	@PostMapping("/ai/multi-query-expander")
	public String multiQueryExpander(@RequestParam("question") String question, 
										      @RequestParam("score") double score, 
										      @RequestParam("source") String source,
										      HttpSession session) {
		
		String answer = service.chatWithTranslationQuery(question, score, source, session.getId());
		
		
		return answer;
	}
}













