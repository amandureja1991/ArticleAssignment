package com.assignment.article.service;

import com.assignment.article.domain.Article;
import com.assignment.article.rest.request.dto.CreateArticleRequestDTO;
import com.assignment.article.rest.response.dto.ArticleResponseDTO;

public class ArticleFactory {

    public static Article buildArticle(CreateArticleRequestDTO requestDto) {
        Article article = new Article();
        article.setBody(requestDto.getBody());
        article.setDescription(requestDto.getDescription());
        article.setTitle(requestDto.getTitle());
        requestDto.getTags().stream().forEach(article::addTag);
        return article;
    }

    public static ArticleResponseDTO buildBasicResponse(Article article) {
        ArticleResponseDTO responseDto = new ArticleResponseDTO();
        responseDto.setSlug(article.getSlug());
        responseDto.setBody(article.getBody());
        responseDto.setDescription(article.getDescription());
        responseDto.setTitle(article.getTitle());
        article.getTags().stream().forEach(tag -> responseDto.addTag(tag.getTag()));
        return responseDto;
    }

    public static int countWords(String string) {
        int count = 0;

        char[] ch = new char[string.length()];
        for (int i = 0; i < string.length(); i++) {
            ch[i] = string.charAt(i);
            if (((i > 0) && (ch[i] != ' ') && (ch[i - 1] == ' ')) || ((ch[0] != ' ') && (i == 0)))
                count++;
        }
        return count;
    }

}
