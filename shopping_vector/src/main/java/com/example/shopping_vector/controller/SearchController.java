package com.example.shopping_vector.controller;

import com.example.shopping_vector.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

    private final DocumentService documentService;

    @GetMapping
    public List<Document> search(@RequestParam String query) {

        return documentService.searchDocuments(query);

    }

    @DeleteMapping
    public String delete(@RequestParam String category){

        documentService.deleteByCategory(category);

        return category + " 삭제 완료";
    }

}