package com.example.ch08.service;

import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.embedding.EmbeddingResponseMetadata;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class AddDocumentService {

	
	private final EmbeddingModel embeddingModel;
	
	private final VectorStore vectorStore;
	
	public void addDocument() {
		
		List<Document> documentList = List.of(
			new Document("대통령 선거는 5년마다 있습니다.", Map.of("source", "헌법", "year", 1987)),
			new Document("대통령 임기는 4년 입니다.", Map.of("source", "헌법", "year", 1980)),
			new Document("자동차를 운전하려면 등록하야 합니다.", Map.of("source", "자동차관리법")),
			new Document("대통령은 행정부의 수반입니다.", Map.of("source", "헌법", "year", 1987)),
			new Document("승용차는 정기적인 점검을 받아야 합니다.", Map.of("source", "자동차관리법"))
		);
		
		// 벡터 저장소 저장
		vectorStore.add(documentList);		
	}
	
	public void addDocumentShopping() {

	    // 쇼핑몰 이용 안내 Document 목록 생성
	    List<Document> documents = List.of(

	        new Document(
	            "회원가입은 이메일 주소와 비밀번호를 입력하여 진행할 수 있습니다.",
	            Map.of(
	                "source", "회원이용안내",
	                "category", "회원가입",
	                "year", 2026
	            )
	        ),

	        new Document(
	            "상품 주문은 회원과 비회원 모두 이용할 수 있습니다.",
	            Map.of(
	                "source", "주문이용안내",
	                "category", "주문",
	                "year", 2026
	            )
	        ),

	        new Document(
	            "결제는 신용카드, 계좌이체, 간편결제 방식을 지원합니다.",
	            Map.of(
	                "source", "결제이용안내",
	                "category", "결제",
	                "year", 2026
	            )
	        ),

	        new Document(
	            "상품은 결제 완료 후 평균 2일에서 3일 이내에 배송됩니다.",
	            Map.of(
	                "source", "배송정책",
	                "category", "배송",
	                "year", 2026
	            )
	        ),

	        new Document(
	            "배송비는 주문 금액이 5만 원 이상이면 무료입니다.",
	            Map.of(
	                "source", "배송정책",
	                "category", "배송비",
	                "year", 2026
	            )
	        ),

	        new Document(
	            "상품 교환은 상품을 받은 날부터 7일 이내에 신청해야 합니다.",
	            Map.of(
	                "source", "교환환불정책",
	                "category", "교환",
	                "year", 2026
	            )
	        ),

	        new Document(
	            "상품 환불은 반품 상품 확인 후 영업일 기준 3일 이내에 처리됩니다.",
	            Map.of(
	                "source", "교환환불정책",
	                "category", "환불",
	                "year", 2026
	            )
	        ),

	        new Document(
	            "고객의 단순 변심으로 반품하는 경우 반품 배송비는 고객이 부담합니다.",
	            Map.of(
	                "source", "교환환불정책",
	                "category", "반품",
	                "year", 2026
	            )
	        ),

	        new Document(
	            "주문한 상품이 배송되기 전에는 주문 취소를 신청할 수 있습니다.",
	            Map.of(
	                "source", "주문취소정책",
	                "category", "주문취소",
	                "year", 2026
	            )
	        ),

	        new Document(
	            "상품 문의와 배송 문의는 고객센터 게시판을 통해 접수할 수 있습니다.",
	            Map.of(
	                "source", "고객센터이용안내",
	                "category", "고객문의",
	                "year", 2026
	            )
	        )
	    );

	    // 벡터 저장소에 Document 목록 저장
	    vectorStore.add(documents);
	}
	
	
}





