package com.example.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.service.BoomBarrierToolsService;
import com.example.service.DateTimeToolsService;
import com.example.service.HeatingSystemToolsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Controller
public class BoomBarrierToolsController {
	
	private final BoomBarrierToolsService service;
	
	@GetMapping("/ai/boom-barrier-tools")
	public String boomBarrierTools() {
		return "/boom-barrier-tools";
	}	
	
	@ResponseBody
	@PostMapping("/ai/boom-barrier-tools")
	public String boomBarrierTools(@RequestParam("attach") MultipartFile attach) throws IOException {
		
		String answer = service.chat(attach.getContentType(), attach.getBytes());
		
		return answer;
	}
	
}








