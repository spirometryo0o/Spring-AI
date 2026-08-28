package com.example.ch04.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.ch04.service.MapOutputConverterService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor
@Controller
public class MapOutputConverterController {
	
	private final MapOutputConverterService service;
	
	@GetMapping("/ai/map-output-converter")
	public String mapOutputConverter() {
		return "/map-output-converter";
	}
	
	@ResponseBody
	@PostMapping("/ai/map-output-converter")
	public Map<String, Object> mapOutputConverter(@RequestParam("hotel") String hotel) {
		
		log.info(hotel);
		
		
		Map<String, Object> hotelInfo = service.convertLowLevel(hotel);
		//Map<String, Object> hotelInfo = service.convertHighLevel(hotel);
		
		return hotelInfo;
	}

}
