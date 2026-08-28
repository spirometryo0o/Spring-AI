package kr.co.sboard.ch05.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

public class SttTtsController {

    @GetMapping("/ai/stt-tts")
    public String sttTts() {
        return "/stt-tts";
    }

    @ResponseBody
    @PostMapping("/ai/stt")
    public String stt(@RequestParam("speech")MultipartFile speech) {

        return null;
    }
}
