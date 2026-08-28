package com.example.dto;

public record YoutubeVideo(
        String title,
        String uploadDate,
        String link,
        String thumbnail
) {
}