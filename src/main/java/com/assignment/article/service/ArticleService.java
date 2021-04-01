package com.assignment.article.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.assignment.article.domain.Article;
import com.assignment.article.exceptions.ArticleNotFoundException;
import com.assignment.article.exceptions.SimilarBodyAlreadyExistException;
import com.assignment.article.exceptions.SlugIdAlreadyExistsException;
import com.assignment.article.repository.ArticleRepository;
import com.assignment.article.rest.request.dto.CreateArticleRequestDTO;
import com.assignment.article.rest.request.dto.UpdateArticleRequestDTO;
import com.assignment.article.rest.response.dto.ArticlePage;
import com.assignment.article.rest.response.dto.ArticleResponseDTO;
import com.assignment.article.rest.response.dto.CreatedArticleResponseDTO;
import com.assignment.article.rest.response.dto.GetArticleResponseDTO;
import com.assignment.article.rest.response.dto.TimeToReadArticleResponseDTO;
import com.assignment.article.rest.response.dto.UpdatedArticleResponseDTO;

import net.ricecode.similarity.JaroWinklerStrategy;
import net.ricecode.similarity.StringSimilarityService;
import net.ricecode.similarity.StringSimilarityServiceImpl;

@Service
public class ArticleService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleService.class);

    @Autowired
    private ArticleRepository repository;

    @Value("${average.speed.human.read.words:10}")
    private Integer noOfWordsToReadPerMinuteByHuman;

    public CreatedArticleResponseDTO save(String slug, CreateArticleRequestDTO requestDto) {
        try {
            findBySlug(slug);
            // throw exception if article already exists
            throw new SlugIdAlreadyExistsException(slug);
        } catch (ArticleNotFoundException exception) {
            // create article
            validateRequest(requestDto.getBody(), null);
            Article article = ArticleFactory.buildArticle(requestDto);
            article.setSlug(slug);
            Article savedArticle = this.repository.save(article);
            return new CreatedArticleResponseDTO(ArticleFactory.buildBasicResponse(savedArticle));
        }

    }

    private Article findBySlug(String slugId) {
        Optional<Article> article = this.repository.findBySlug(slugId);
        if (article.isPresent()) {
            return article.get();
        } else {
            throw new ArticleNotFoundException(slugId);
        }
    }

    private void validateRequest(String body, String slugId) {
        List<String> articles = null;
        if (slugId == null)
            articles = this.repository.getBody();
        else
            articles = this.repository.getBodyExceptSlugId(slugId);
        StringSimilarityService stringSimilarityService = new StringSimilarityServiceImpl(new JaroWinklerStrategy());
        double score = 0;
        for (String existingBody : articles) {
            score = stringSimilarityService.score(existingBody, body);
            if ((score * 100) > 70)
                throw new SimilarBodyAlreadyExistException();
        }
    }

    public GetArticleResponseDTO get(String slugId) {
        Article article = findBySlug(slugId);
        return new GetArticleResponseDTO(ArticleFactory.buildBasicResponse(article), article.getUpdatedAt(),
                article.getCreatedAt());
    }

    public void delete(String slugId) {
        this.repository.delete(findBySlug(slugId));
    }

    public UpdatedArticleResponseDTO update(String slugId, UpdateArticleRequestDTO requestDto) {
        Article article = this.findBySlug(slugId);
        validateRequest(requestDto.getBody(), slugId);
        updateValue(article::setDescription, requestDto.getDescription());
        updateValue(article::setBody, requestDto.getBody());
        updateValue(article::setTitle, requestDto.getTitle());
        Article updateArticle = this.repository.save(article);
        return new UpdatedArticleResponseDTO(ArticleFactory.buildBasicResponse(updateArticle),
                updateArticle.getUpdatedAt(), updateArticle.getCreatedAt());
    }

    private <T> void updateValue(Consumer<T> setterMethod, T value) {
        if (value != null) {
            setterMethod.accept(value);
        }
    }

    public ArticlePage getArticles(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<ArticleResponseDTO> reponseData = new ArrayList<>();
        Page<Article> articles = repository.findAll(pageable);
        for (Article article : articles.getContent()) {
            reponseData.add(ArticleFactory.buildBasicResponse(article));
        }
        return new ArticlePage(pageNo, articles.isLast(), articles.getTotalPages(), articles.getTotalElements(),
                reponseData);
    }

    public TimeToReadArticleResponseDTO getTimeToReadArticle(String slug) {
        Article article = findBySlug(slug);
        int noOfWords = ArticleFactory.countWords(article.getBody());
        int mins = noOfWords / noOfWordsToReadPerMinuteByHuman;
        int seconds = (60 / noOfWordsToReadPerMinuteByHuman) * (noOfWords % noOfWordsToReadPerMinuteByHuman);
        return new TimeToReadArticleResponseDTO(slug, mins, seconds);
    }

}
