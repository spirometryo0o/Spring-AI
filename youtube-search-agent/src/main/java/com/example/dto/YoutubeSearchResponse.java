package com.example.dto;

import java.util.List;

public record YoutubeSearchResponse(
        List<YoutubeVideo> videos
) {
}