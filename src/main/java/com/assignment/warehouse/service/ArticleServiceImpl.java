package com.assignment.warehouse.service;

import com.assignment.warehouse.domain.Article;
import com.assignment.warehouse.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService{

    private ArticleRepository vArticleRepository;

    public ArticleServiceImpl(ArticleRepository vArticleRepository) {
        this.vArticleRepository = vArticleRepository;
    }

    public Article save(Article pArticle) {
        return vArticleRepository.save(pArticle);
    }

    public Iterable<Article> list() {
        return vArticleRepository.findAll();
    }

    public Iterable<Article> save(List<Article> pArticles) {

        List<Article> vArtcileSaved = new ArrayList<>();

        List<Article> vArtcilefromDb = vArticleRepository.findAll();

        List<Article> vUpdateArtileList = getUpdatedArticles(pArticles, vArtcilefromDb);
        List<Article> vNewArtileList = getNewArticles(pArticles, vArtcilefromDb);

        if (vUpdateArtileList != null && vUpdateArtileList.size() > 0) {
            vArtcileSaved = vArticleRepository.saveAll(vUpdateArtileList);
        }

        if (vNewArtileList != null && vNewArtileList.size() > 0) {
            vArtcileSaved.addAll(vArticleRepository.saveAll(vNewArtileList));
        }

        return vArtcileSaved;
    }


    // Find the article which is already in database then update the stock
    private List<Article> getUpdatedArticles(List<Article> pArticles, List<Article> vArtcilefromDb) {
        List<Article> vUpdateArtileList = pArticles.stream().filter(articlefromDb -> vArtcilefromDb.stream().anyMatch(article -> article.getArt_id() == articlefromDb.getArt_id())).collect(Collectors.toList());

        vUpdateArtileList.forEach(vUpdateArtile -> {
            // find the first B object with matching art_id:
            Optional<Article> matchingBElem = vArtcilefromDb.stream().filter(bElem -> Objects.equals(vUpdateArtile.getArt_id(), bElem.getArt_id())).findFirst();

            // and use it to set the dob value in this A list element:
            if (matchingBElem.isPresent()) {
                vUpdateArtile.setStock(vUpdateArtile.getStock() + matchingBElem.get().getStock());
            }
        });
        return vUpdateArtileList;
    }


    // Find the New article in JSN request
    private List<Article> getNewArticles(List<Article> pArticles, List<Article> pArtcilefromDb) {
        return pArticles.stream().filter(article -> !pArtcilefromDb.contains(article)).collect(Collectors.toList());
    }
}
