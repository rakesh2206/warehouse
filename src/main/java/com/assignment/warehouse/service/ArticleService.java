package com.assignment.warehouse.service;

import com.assignment.warehouse.domain.Article;

import java.util.List;

public interface ArticleService {

    Article save(Article pArticle);
    Iterable<Article> list();
    Iterable<Article> save(List<Article> pArticles);
}
