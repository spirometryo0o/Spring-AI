package com.example.youtubeserchagent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example")
public class YoutubeSearchAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(YoutubeSearchAgentApplication.class, args);
    }
}