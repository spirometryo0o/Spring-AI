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
import com.example.dto.Attraction;
import com.example.dto.Restaurant;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Controller
public class Ex6RestaurantAgentController {
	
	private final Ex6RestaurantAgent agent;

	@GetMapping("/ai/exam06-restaurant-agent")
	public String template() {
		return "/exam06-restaurant-agent";
	}
	
	@ResponseBody	
	@PostMapping("/ai/exam06-restaurant-agent")
	public List<Restaurant> post(@RequestParam("question") String question) {
		List<Restaurant> attractionList = agent.execute(question);
		return attractionList;
	}
	
}









