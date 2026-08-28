package com.example.ch06.controller;

import java.io.IOException;

import javax.print.DocFlavor.STRING;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.ch06.service.ImageAnalysisService;
import com.example.ch06.service.ImageGenerationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

@Log4j2
@RequiredArgsConstructor
@Controller
public class VideoAnalysisController {
		
		
	@GetMapping("/ai/video-analysis")
	public String videoAnalysis() {
		return "/video-analysis";
	}
	
	
	
	
	
}











