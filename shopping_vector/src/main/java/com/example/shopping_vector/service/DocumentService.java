package com.example.shopping_vector.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DocumentService {

    private final VectorStore vectorStore;

    public DocumentService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    // 샘플 문서 12개 저장
    public void saveSampleDocuments() {

        List<Document> documents = List.of(

                new Document(
                        "상품은 결제 완료 후 평균 2~3일 이내 배송됩니다.",
                        Map.of(
                                "source", "배송정책",
                                "category", "배송",
                                "year", "2026"
                        )
                ),

                new Document(
                        "배송비는 주문 금액이 5만원 이상이면 무료입니다.",
                        Map.of(
                                "source", "배송정책",
                                "category", "배송비",
                                "year", "2026"
                        )
                ),

                new Document(
                        "제주도와 도서산간 지역은 추가 배송비가 발생할 수 있습니다.",
                        Map.of(
                                "source", "배송정책",
                                "category", "배송비",
                                "year", "2026"
                        )
                ),

                new Document(
                        "상품 반품은 상품 수령 후 7일 이내에 신청해야 합니다.",
                        Map.of(
                                "source", "교환환불정책",
                                "category", "반품",
                                "year", "2026"
                        )
                ),

                new Document(
                        "상품 교환은 상품을 사용하지 않은 상태에서 신청할 수 있습니다.",
                        Map.of(
                                "source", "교환환불정책",
                                "category", "교환",
                                "year", "2026"
                        )
                ),

                new Document(
                        "고객의 단순 변심으로 인한 반품 배송비는 고객이 부담합니다.",
                        Map.of(
                                "source", "교환환불정책",
                                "category", "반품",
                                "year", "2026"
                        )
                ),

                new Document(
                        "결제 취소 금액은 결제수단에 따라 영업일 기준 3일에서 5일 이내에 환불됩니다.",
                        Map.of(
                                "source", "결제정책",
                                "category", "환불",
                                "year", "2026"
                        )
                ),

                new Document(
                        "회원가입은 이메일과 비밀번호를 입력하여 진행합니다.",
                        Map.of(
                                "source", "회원정책",
                                "category", "회원가입",
                                "year", "2026"
                        )
                ),

                new Document(
                        "비밀번호를 잊어버린 경우 가입한 이메일을 통해 재설정할 수 있습니다.",
                        Map.of(
                                "source", "회원정책",
                                "category", "비밀번호",
                                "year", "2026"
                        )
                ),

                new Document(
                        "주문한 상품이 배송되기 전에는 주문 취소를 신청할 수 있습니다.",
                        Map.of(
                                "source", "주문취소정책",
                                "category", "주문취소",
                                "year", "2026"
                        )
                ),

                new Document(
                        "상품 문의와 배송 문의는 고객센터 게시판을 통해 접수할 수 있습니다.",
                        Map.of(
                                "source", "고객센터이용안내",
                                "category", "고객센터",
                                "year", "2026"
                        )
                ),

                new Document(
                        "품절된 상품은 재입고 알림을 신청할 수 있습니다.",
                        Map.of(
                                "source", "상품이용안내",
                                "category", "재입고",
                                "year", "2026"
                        )
                )
        );

        vectorStore.add(documents);

        System.out.println("문서 12개 저장 완료");
    }

    // 질문과 의미가 비슷한 문서 검색
    public List<Document> searchDocuments(String query) {

        SearchRequest searchRequest = SearchRequest.builder()
                .query(query)
                .topK(5)
                .similarityThreshold(0.5)
                .build();

        return vectorStore.similaritySearch(searchRequest);
    }

    // category 메타데이터를 기준으로 문서 삭제
    public void deleteByCategory(String category) {

        String filterExpression =
                "category == '" + category + "'";

        vectorStore.delete(filterExpression);
    }
}