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
import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer;
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
public class RewriteQueryTransformerService {
	
	private ChatClient chatClient;	
	private ChatModel chatModel;
	private ChatMemory chatMemory;
	private VectorStore vectorStore;
	
	public RewriteQueryTransformerService(ChatClient.Builder chatClientBuilder,
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
	
	public RewriteQueryTransformer createRewriteQueryTransformer() {
		
		ChatClient.Builder chatClientBuilder = ChatClient
												.builder(chatModel)
												.defaultAdvisors(
													new SimpleLoggerAdvisor(Ordered.LOWEST_PRECEDENCE - 1)
												);
		
		// 질문 재작성기 생성
		RewriteQueryTransformer rewriteQueryTransformer = RewriteQueryTransformer
															.builder()
															.chatClientBuilder(chatClientBuilder)
															.build();
		
		return rewriteQueryTransformer;
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
		
	public String chatWithRewriteQuery(String question, double score, String source, String conversationId) {
		
		RetrievalAugmentationAdvisor retrievalAugmentationAdvisor = RetrievalAugmentationAdvisor.builder()
																		.queryTransformers(createRewriteQueryTransformer())
																		.documentRetriever(createVectorStoreDocumentRetriever(score, source))
																		.build();
		
	    // 프롬프트를 LLM으로 전송하고 응답을 받는 코드
	    String answer = this.chatClient.prompt()	    	
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
}











