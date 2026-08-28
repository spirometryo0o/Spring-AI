package com.example.agent;

import com.example.dto.YoutubeVideo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class Exam08YoutubeSearchAgent {

    /*
     * AI의 역할, 사용할 도구, 최종 응답 형식을 지정한다.
     */
    private static final String SYSTEM_PROMPT = """
            당신은 여행 관련 YouTube 비디오 검색 전문 에이전트입니다.

            ## 목표
            사용자의 요청에 맞는 YouTube 비디오를 검색하여 추천합니다.

            ## 사용 가능한 도구
            searchYoutubeVideos: YouTube 비디오 검색

            ## 작업 규칙
            - 사용자가 YouTube 영상 검색을 요청하면 반드시 searchYoutubeVideos 도구를 사용하세요.
            - 도구가 반환한 검색 결과만 사용하세요.
            - 존재하지 않는 영상이나 링크를 임의로 만들지 마세요.
            - 검색 결과 중 최대 6개를 반환하세요.

            ## 출력 형식
            - 반드시 JSON 배열 형식으로만 출력하세요.
            - 설명이나 마크다운 코드 블록을 추가하지 마세요.
            - 각 객체는 다음 필드를 모두 포함해야 합니다.
              title, uploadDate, link, thumbnail

            ## 출력 예시
            [
              {
                "title": "부산 여행 영상",
                "uploadDate": "2 years ago",
                "link": "https://www.youtube.com/watch?v=...",
                "thumbnail": "https://..."
              }
            ]
            """;

    private final ChatClient chatClient;
    private final String serpApiKey;
    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Exam08YoutubeSearchAgent(
            ChatClient.Builder chatClientBuilder,
            @Value("${serpapi.endpoint}") String serpApiEndpoint,
            @Value("${serpapi.api-key}") String serpApiKey,
            WebClient.Builder webClientBuilder
    ) {
        this.serpApiKey = serpApiKey;

        /*
         * SerpApi 요청에 사용할 WebClient를 만든다.
         */
        this.webClient = webClientBuilder
                .baseUrl(serpApiEndpoint)
                .defaultHeader("Accept", "application/json")
                .build();

        /*
         * 시스템 프롬프트를 적용한 ChatClient를 만든다.
         */
        this.chatClient = chatClientBuilder
                .defaultSystem(SYSTEM_PROMPT)
                .build();
    }

    /*
     * 사용자 질문을 AI에게 전달하는 실행 진입점이다.
     *
     * .tools(this)
     * 현재 클래스에 있는 @Tool 메서드를 AI가 사용할 수 있게 한다.
     *
     * .entity(...)
     * AI가 출력한 JSON을 List<YoutubeVideo>로 변환한다.
     */
    public List<YoutubeVideo> execute(String userQuery) {

        if (userQuery == null || userQuery.isBlank()) {
            throw new IllegalArgumentException("검색 질문을 입력해주세요.");
        }

        List<YoutubeVideo> result = chatClient.prompt()
                .user(userQuery.trim())
                .tools(this)
                .call()
                .entity(new ParameterizedTypeReference<List<YoutubeVideo>>() {
                });

        return result == null ? List.of() : result;
    }

    /*
     * AI가 호출할 수 있는 YouTube 검색 도구다.
     */
    @Tool(description = """
            YouTube에서 여행, 관광지, 맛집, 카페 등과 관련된 영상을 검색합니다.
            사용자가 특정 지역이나 여행 주제의 YouTube 영상을 요청할 때 사용합니다.
            """)
    public String searchYoutubeVideos(
            @ToolParam(description = "YouTube에서 검색할 키워드. 예: 부산 여행, 제주도 맛집")
            String query
    ) {
        if (query == null || query.isBlank()) {
            throw new IllegalArgumentException("YouTube 검색어가 비어 있습니다.");
        }

        try {
            String responseBody = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("engine", "youtube")
                            .queryParam("search_query", query.trim())
                            .queryParam("api_key", serpApiKey)
                            .build())
                    .retrieve()
                    .onStatus(
                            status -> status.value() == 401,
                            response -> response.bodyToMono(String.class)
                                    .map(body -> new RuntimeException(
                                            "SerpApi 인증에 실패했습니다. API 키를 확인해주세요."
                                    ))
                    )
                    .onStatus(
                            status -> status.value() == 403,
                            response -> response.bodyToMono(String.class)
                                    .map(body -> new RuntimeException(
                                            "SerpApi 사용 권한 또는 할당량을 확인해주세요."
                                    ))
                    )
                    .onStatus(
                            status -> status.value() == 429,
                            response -> response.bodyToMono(String.class)
                                    .map(body -> new RuntimeException(
                                            "SerpApi 요청 한도를 초과했습니다."
                                    ))
                    )
                    .onStatus(
                            status -> status.isError(),
                            response -> response.bodyToMono(String.class)
                                    .map(body -> new RuntimeException(
                                            "SerpApi 요청 중 오류가 발생했습니다: " + body
                                    ))
                    )
                    .bodyToMono(String.class)
                    .block();

            if (responseBody == null || responseBody.isBlank()) {
                return "[]";
            }

            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode videoResults = rootNode.path("video_results");

            if (!videoResults.isArray()) {
                return "[]";
            }

            ArrayNode resultArray = objectMapper.createArrayNode();

            int count = 0;

            for (JsonNode videoNode : videoResults) {
                if (count >= 6) {
                    break;
                }

                String title = videoNode.path("title").asText("");
                String link = videoNode.path("link").asText("");
                String uploadDate = videoNode.path("published_date")
                        .asText("업로드 날짜 정보 없음");

                String thumbnail = extractThumbnail(videoNode.path("thumbnail"));

                /*
                 * 제목이나 링크가 없는 검색 결과는 제외한다.
                 */
                if (title.isBlank() || link.isBlank()) {
                    continue;
                }

                ObjectNode resultVideo = objectMapper.createObjectNode();

                resultVideo.put("title", title);
                resultVideo.put("uploadDate", uploadDate);
                resultVideo.put("link", link);
                resultVideo.put("thumbnail", thumbnail);

                resultArray.add(resultVideo);
                count++;
            }

            return objectMapper.writeValueAsString(resultArray);

        } catch (Exception e) {
            throw new RuntimeException(
                    "YouTube 영상을 검색하는 중 오류가 발생했습니다: " + e.getMessage(),
                    e
            );
        }
    }

    /*
     * SerpApi 응답에서 thumbnail이 문자열 또는 객체로 올 수 있으므로
     * 두 경우를 모두 처리한다.
     */
    private String extractThumbnail(JsonNode thumbnailNode) {

        if (thumbnailNode == null || thumbnailNode.isMissingNode()) {
            return "";
        }

        if (thumbnailNode.isTextual()) {
            return thumbnailNode.asText("");
        }

        if (thumbnailNode.isObject()) {
            String staticThumbnail = thumbnailNode.path("static").asText("");

            if (!staticThumbnail.isBlank()) {
                return staticThumbnail;
            }

            return thumbnailNode.path("rich").asText("");
        }

        return "";
    }
}