package com.example.controller;

import com.example.agent.Exam08YoutubeSearchAgent;
import com.example.dto.YoutubeVideo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/exam08")
public class Exam08YoutubeSearchAgentController {

    private final Exam08YoutubeSearchAgent youtubeSearchAgent;

    public Exam08YoutubeSearchAgentController(
            Exam08YoutubeSearchAgent youtubeSearchAgent
    ) {
        this.youtubeSearchAgent = youtubeSearchAgent;
    }

    /*
     * 검색 화면을 연다.
     */
    @GetMapping
    public String page() {
        return "exam08-youtube-search";
    }

    /*
     * 사용자의 질문을 받아 AI Agent를 실행한다.
     */
    @ResponseBody
    @PostMapping("/search")
    public ResponseEntity<?> search(
            @RequestBody Map<String, String> request
    ) {
        String question = request.get("question");

        if (question == null || question.isBlank()) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "message",
                            "검색할 내용을 입력해주세요."
                    ));
        }

        try {
            List<YoutubeVideo> videos =
                    youtubeSearchAgent.execute(question);

            return ResponseEntity.ok(videos);

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "message",
                            e.getMessage()
                    ));

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "message",
                            e.getMessage()
                    ));
        }
    }
}