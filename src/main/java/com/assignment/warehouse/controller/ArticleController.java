package com.assignment.warehouse.controller;

import com.assignment.warehouse.domain.Article;
import com.assignment.warehouse.service.ArticleService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
public class ArticleController {

    private ArticleService vArticleService;

    public ArticleController(ArticleService pArticleService) {
        this.vArticleService = pArticleService;
    }

    @GetMapping("/list")
    public Iterable<Article> list() {
        return vArticleService.list();
    }

    @PostMapping("/addArticles")
    public HttpStatus addArticless(@RequestBody String pJsonPath) {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Article>> typeReference = new TypeReference<List<Article>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/inventory.json");
        try{
            List<Article> vArticles = mapper.readValue(inputStream, typeReference);
           vArticleService.save(vArticles);
            System.out.println("Stock Updated");
            return HttpStatus.OK;

        } catch (IOException e){
            System.out.println("Unable to Save Articles " + e.getMessage());
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
