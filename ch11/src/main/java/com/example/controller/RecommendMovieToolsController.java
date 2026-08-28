package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.service.DateTimeToolsService;
import com.example.service.HeatingSystemToolsService;
import com.example.service.RecommendMovieToolsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Controller
public class RecommendMovieToolsController {
	
	private final RecommendMovieToolsService service;
	
	@GetMapping("/ai/recommend-movie-tools")
	public String recommendMovieTools() {
		return "/recommend-movie-tools";
	}	
	
	@ResponseBody
	@PostMapping("/ai/recommend-movie-tools")
	public String recommendMovieTools(@RequestParam("question") String question) {
		String answer = service.chat(question);
		return answer;
	}
	
}








