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
import com.example.service.TxtPdfWordEtlService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Controller
public class CompressionQueryTransformerController {

	private final CompressionQueryTransformerService service;
	
	
	@GetMapping("/ai/compression-query-transformer")
	public String compressionQueryTransformer() {		
		return "/compression-query-transformer";				
	}
		
	@ResponseBody
	@PostMapping("/ai/compression-query-transformer")
	public String compressionQueryTransformer(@RequestParam("question") String question, 
										      @RequestParam("score") double score, 
										      @RequestParam("source") String source,
										      HttpSession session) {
		
		String answer = service.chatWithCompression2(question, score, source, session.getId());
		
		
		return answer;
	}
}













