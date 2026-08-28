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
public class ImageGenerationController {
	
	private final ImageGenerationService service;
		
	@GetMapping("/ai/image-generation")
	public String imageGeneration() {
		return "/image-generation";
	}
	
	
	@ResponseBody
	@PostMapping(
		value = "/ai/image-generate",
		consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
		produces = MediaType.TEXT_PLAIN_VALUE
	)
	public String imageGeneration(@RequestParam("description") String description) {
		
		String b64Json = service.generate(description);
				
		return b64Json;
	}
	
	@ResponseBody
	@PostMapping(
		value = "/ai/image-edit",
		consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
		produces = MediaType.TEXT_PLAIN_VALUE
	)
	public String imageEdit(@RequestParam("description")   String description,
							@RequestParam("originalImage") MultipartFile originalImage,
							@RequestParam("maskImage") MultipartFile maskImage) throws IOException {
		
		
		String b64Json = service.edit(description, originalImage.getBytes(), maskImage.getBytes());
		
		//log.info(b64Json);
		
		return b64Json;
			
				
		
	}
	
	
}











