package com.example.service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.document.DocumentTransformer;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.preretrieval.query.transformation.CompressionQueryTransformer;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.core.Ordered;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class CompressionQueryTransformerService {
	
	private ChatClient chatClient;	
	private ChatModel chatModel;
	private ChatMemory chatMemory;
	private VectorStore vectorStore;
	
	public CompressionQueryTransformerService(ChatClient.Builder chatClientBuilder,
											  ChatModel chatModel,
											  ChatMemory chatMemory,
											  VectorStore vectorStore) {
		
		this.chatClient = chatClientBuilder
							.defaultAdvisors(
								SimpleLoggerAdvisor
									.builder()
									.order(Ordered.LOWEST_PRECEDENCE - 1)
									.build()
							)							
							.build();
		
		this.chatModel = chatModel;
		this.chatMemory = chatMemory;
		this.vectorStore = vectorStore;
	}
	
	public CompressionQueryTransformer createCompressionQueryTransformer() {
		
		ChatClient.Builder chatClientBuilder = ChatClient
												.builder(chatModel)
												.defaultAdvisors(
													new SimpleLoggerAdvisor(Ordered.LOWEST_PRECEDENCE - 1)
												);
		
		// 압축쿼리변환기 생성
		CompressionQueryTransformer compressionQueryTransformer = CompressionQueryTransformer
																		.builder()
																		.chatClientBuilder(chatClientBuilder)
																		.build();
		
		return compressionQueryTransformer;
	}
	
	public VectorStoreDocumentRetriever createVectorStoreDocumentRetriever(double score, String source) {
		
		VectorStoreDocumentRetriever vectorStoreDocumentRetriever = VectorStoreDocumentRetriever
																		.builder()
																		.vectorStore(vectorStore)
																		.similarityThreshold(score)
																		.topK(10)																		
																		.build();
				
		return vectorStoreDocumentRetriever;
		
	}
		
	public String chatWithCompression(String question, double score, String source, String conversationId) {
		
		RetrievalAugmentationAdvisor retrievalAugmentationAdvisor = RetrievalAugmentationAdvisor.builder()
																		.queryTransformers(createCompressionQueryTransformer())
																		.documentRetriever(createVectorStoreDocumentRetriever(score, source))
																		.build();	
		
	    // 프롬프트를 LLM으로 전송하고 응답을 받는 코드
	    String answer = this.chatClient.prompt()
	    	.system("""
	                후속 질문을 아래 규칙을 참고하세요.
	    			
	                규칙:
	                - 이전 대화를 참고하세요.
	                - 사용자가 묻지 않은 내용을 추가하지 마세요.
	                - 질문의 의미를 확장하지 마세요.
	                - 길게 답변하지 마세요.
	                """)
	        .user(question)
	        .advisors(
	        	MessageChatMemoryAdvisor.builder(chatMemory).build(),
	        	retrievalAugmentationAdvisor
	        )
	        .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, conversationId))
	        .call()
	        .content();
	    
	    return answer;
	  }
	
	public String chatWithCompression2(
	        String question,
	        double score,
	        String source,
	        String conversationId) {

	    MessageChatMemoryAdvisor memoryAdvisor =
	        MessageChatMemoryAdvisor.builder(chatMemory)
	            .order(Ordered.HIGHEST_PRECEDENCE + 100)
	            .build();

	    RetrievalAugmentationAdvisor retrievalAdvisor =
	        RetrievalAugmentationAdvisor.builder()
	            .queryTransformers(createCompressionQueryTransformer())
	            .documentRetriever(
	                createVectorStoreDocumentRetriever(score, source)
	            )
	            .order(Ordered.HIGHEST_PRECEDENCE + 200)
	            .build();

	    return this.chatClient.prompt()
	        .user(question)
	        .advisors(advisorSpec -> advisorSpec
	            .advisors(
	                memoryAdvisor,
	                retrievalAdvisor
	            )
	            .param(
	                ChatMemory.CONVERSATION_ID,
	                conversationId
	            )
	        )
	        .call()
	        .content();
	}
	
}











