package com.example.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.agent.Ex1WeatherAgent;
import com.example.agent.Ex2WeatherAgent;
import com.example.agent.Ex3WeatherAgent;
import com.example.agent.Ex4WeatherAgent;
import com.example.agent.Ex5AttractionAgent;
import com.example.agent.Ex6RestaurantAgent;
import com.example.agent.Ex7AccommodationAgent;
import com.example.agent.Ex8YoutubeSearchAgent;
import com.example.dto.Accommodation;
import com.example.dto.Attraction;
import com.example.dto.Restaurant;
import com.example.dto.Youtube;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Controller
public class Ex8YoutubeSearchAgentController {
	
	// 교재 p453 참고
	private final Ex8YoutubeSearchAgent agent;

	@GetMapping("/ai/exam08-youtube-search-agent")
	public String template() {
		return "/exam08-youtube-search-agent";
	}
	
	@ResponseBody	
	@PostMapping("/ai/exam08-youtube-search")
	public List<Youtube> post(@RequestParam("question") String question) {
		List<Youtube> youtubeList = agent.execute(question);
		return youtubeList;
	}
	
}









