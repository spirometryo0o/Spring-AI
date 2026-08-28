package com.example.tool;

import java.util.List;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class BoomBarrierTools {

	// 교재 p370
	@Tool(description = "차단기를 올립니다.")
	public String boomBarrierUp() {
		log.info("차단기를 올립니다.");
		return "차단기 올림";
	}

	@Tool(description = "차단기를 내립니다.")
	public String boomBarrierDown() {
		log.info("차단기를 내립니다.");
		return "차단기 내림";
	}
}
