package com.assignment.article.rest.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.article.rest.request.dto.CreateArticleRequestDTO;
import com.assignment.article.rest.request.dto.UpdateArticleRequestDTO;
import com.assignment.article.rest.response.dto.ArticlePage;
import com.assignment.article.rest.response.dto.CreatedArticleResponseDTO;
import com.assignment.article.rest.response.dto.GetArticleResponseDTO;
import com.assignment.article.rest.response.dto.TimeToReadArticleResponseDTO;
import com.assignment.article.rest.response.dto.UpdatedArticleResponseDTO;
import com.assignment.article.service.ArticleService;

@RestController
@RequestMapping("/article")
public class ArticleRestController {

    @Autowired
    private ArticleService service;

    @PostMapping("/{slug}")
    public CreatedArticleResponseDTO save(@Valid @PathVariable String slug,
            @Valid @RequestBody CreateArticleRequestDTO createRequest) {
        return this.service.save(slug, createRequest);
    }

    @GetMapping("/{slug}")
    public GetArticleResponseDTO get(@Valid @PathVariable String slug) {
        return this.service.get(slug);
    }

    @GetMapping
    public ArticlePage getAllByPageable(@RequestParam(defaultValue = "0", required = false, name = "pageNo") int pageNo,
            @RequestParam(defaultValue = "10", required = false, name = "pageSize") int pageSize) {
        return this.service.getArticles(pageNo, pageSize);
    }

    @PutMapping("/{slug}")
    public UpdatedArticleResponseDTO update(@Valid @PathVariable String slug,
            @Valid @RequestBody UpdateArticleRequestDTO requestDto) {
        return this.service.update(slug, requestDto);
    }

    @DeleteMapping("/{slug}")
    public void delete(@Valid @PathVariable String slug) {
        this.service.delete(slug);
    }

    @GetMapping("/time/{slug}")
    public TimeToReadArticleResponseDTO getTimeToReadArticle(@Valid @PathVariable String slug) {
        return this.service.getTimeToReadArticle(slug);
    }
}
