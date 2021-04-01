package com.assignment.article.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.assignment.article.domain.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

    public Optional<Article> findBySlug(String slug);

    @Query("Select body from Article")
    public List<String> getBody();

    @Query("Select body from Article where slug != :slug")
    public List<String> getBodyExceptSlugId(String slug);

}
