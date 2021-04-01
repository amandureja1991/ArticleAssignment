package com.assignment.article.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.assignment.article.domain.Article;
import com.assignment.article.exceptions.ArticleNotFoundException;
import com.assignment.article.exceptions.SimilarBodyAlreadyExistException;
import com.assignment.article.exceptions.SlugIdAlreadyExistsException;
import com.assignment.article.repository.ArticleRepository;
import com.assignment.article.rest.request.dto.CreateArticleRequestDTO;
import com.assignment.article.rest.request.dto.UpdateArticleRequestDTO;
import com.assignment.article.rest.response.dto.ArticlePage;
import com.assignment.article.rest.response.dto.CreatedArticleResponseDTO;
import com.assignment.article.rest.response.dto.GetArticleResponseDTO;
import com.assignment.article.rest.response.dto.TimeToReadArticleResponseDTO;
import com.assignment.article.rest.response.dto.UpdatedArticleResponseDTO;
import com.assignment.article.rest.utils.TestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
public class ArticleServiceTest {

    @InjectMocks
    private ArticleService service;

    @Mock
    private ArticleRepository mockRepository;

    @Captor
    ArgumentCaptor<Pageable> pageableCaptor;

    @Test
    public void testSave() {
        CreateArticleRequestDTO requestDTO = TestUtils.buildCreateArticleRequestDTO();
        List<String> body = Collections.singletonList("ABC");
        Mockito.when(this.mockRepository.getBody()).thenReturn(body);
        Mockito.when(this.mockRepository.save(Mockito.any())).thenReturn(ArticleFactory.buildArticle(requestDTO));
        CreatedArticleResponseDTO actualResponse = this.service.save(TestUtils.SAMPLE_SLUG_ID, requestDTO);
        Assert.assertEquals(requestDTO.getBody(), actualResponse.getBody());
        Assert.assertEquals(requestDTO.getTitle(), actualResponse.getTitle());
        Assert.assertEquals(requestDTO.getDescription(), actualResponse.getDescription());
        Assert.assertEquals(requestDTO.getTags(), actualResponse.getTags());
    }

    @Test(expected = SlugIdAlreadyExistsException.class)
    public void testSaveIfSlugIdAlreadyExists() {
        CreateArticleRequestDTO requestDTO = TestUtils.buildCreateArticleRequestDTO();
        Mockito.when(this.mockRepository.findBySlug(Mockito.anyString()))
                .thenReturn(Optional.of(ArticleFactory.buildArticle(requestDTO)));
        this.service.save(TestUtils.SAMPLE_SLUG_ID, requestDTO);
    }

    @Test(expected = SimilarBodyAlreadyExistException.class)
    public void testSaveIfSimilarBodyAlreadyExist() {
        CreateArticleRequestDTO requestDTO = TestUtils.buildCreateArticleRequestDTO();
        List<String> body = Collections.singletonList(requestDTO.getBody());
        Mockito.when(this.mockRepository.getBody()).thenReturn(body);
        this.service.save(TestUtils.SAMPLE_SLUG_ID, requestDTO);
    }

    @Test(expected = ArticleNotFoundException.class)
    public void testGetIfArticleNotExist() {
        Mockito.when(this.mockRepository.findBySlug(Mockito.anyString())).thenReturn(Optional.empty());
        this.service.get(TestUtils.SAMPLE_SLUG_ID);
    }

    @Test
    public void testGet() {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        Article article = ArticleFactory.buildArticle(TestUtils.buildCreateArticleRequestDTO());
        article.setCreatedAt(currentTimestamp);
        article.setUpdatedAt(currentTimestamp);
        GetArticleResponseDTO getResponse = new GetArticleResponseDTO(ArticleFactory.buildBasicResponse(article),
                article.getCreatedAt(), article.getUpdatedAt());
        Mockito.when(this.mockRepository.findBySlug(Mockito.anyString())).thenReturn(Optional.of(article));
        GetArticleResponseDTO actualResponse = this.service.get(TestUtils.SAMPLE_SLUG_ID);
        Assert.assertEquals(getResponse.getBody(), actualResponse.getBody());
        Assert.assertEquals(getResponse.getTitle(), actualResponse.getTitle());
        Assert.assertEquals(getResponse.getDescription(), actualResponse.getDescription());
        Assert.assertEquals(getResponse.getTags(), actualResponse.getTags());
        Assert.assertEquals(getResponse.getCreatedAt(), actualResponse.getCreatedAt());
        Assert.assertEquals(getResponse.getUpdatedAt(), actualResponse.getUpdatedAt());
    }

    @Test
    public void testDelete() {
        Mockito.when(this.mockRepository.findBySlug(Mockito.anyString()))
                .thenReturn(Optional.of(ArticleFactory.buildArticle(TestUtils.buildCreateArticleRequestDTO())));
        this.service.delete(TestUtils.SAMPLE_SLUG_ID);
        Mockito.verify(this.mockRepository).delete(Mockito.any());
    }

    @Test(expected = ArticleNotFoundException.class)
    public void testDeleteIfArticleNotFound() {
        Mockito.when(this.mockRepository.findBySlug(Mockito.anyString())).thenReturn(Optional.empty());
        this.service.delete(TestUtils.SAMPLE_SLUG_ID);
    }

    @Test(expected = ArticleNotFoundException.class)
    public void testUpdateIfArticleNotFound() {
        Mockito.when(this.mockRepository.findBySlug(Mockito.anyString())).thenReturn(Optional.empty());
        this.service.update(TestUtils.SAMPLE_SLUG_ID, new UpdateArticleRequestDTO());
    }

    @Test
    public void testUpdate() {
        UpdateArticleRequestDTO requestDTO = TestUtils.buildUpdateArticleRequestDTO();

        Article article = new Article();
        article.setBody(requestDTO.getBody());
        article.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        article.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        article.setDescription(requestDTO.getDescription());
        article.setTitle(requestDTO.getTitle());

        List<String> body = Collections.singletonList("ABC");
        Mockito.when(this.mockRepository.getBody()).thenReturn(body);
        Mockito.when(this.mockRepository.findBySlug(Mockito.anyString()))
                .thenReturn(Optional.of(ArticleFactory.buildArticle(TestUtils.buildCreateArticleRequestDTO())));
        Mockito.when(this.mockRepository.save(Mockito.any())).thenReturn(article);
        UpdatedArticleResponseDTO actualResponse = this.service.update(TestUtils.SAMPLE_SLUG_ID, requestDTO);
        Assert.assertEquals(requestDTO.getBody(), actualResponse.getBody());
        Assert.assertEquals(requestDTO.getTitle(), actualResponse.getTitle());
        Assert.assertEquals(requestDTO.getDescription(), actualResponse.getDescription());
        Assert.assertNotNull(actualResponse.getTags());
        Assert.assertNotNull(actualResponse.getCreatedAt());
        Assert.assertNotNull(actualResponse.getUpdatedAt());
    }

    @Test(expected = SimilarBodyAlreadyExistException.class)
    public void testUpdateIfSimilarBodyAlreadyExist() {
        UpdateArticleRequestDTO requestDTO = TestUtils.buildUpdateArticleRequestDTO();
        List<String> body = Collections.singletonList(requestDTO.getBody());
        Mockito.when(this.mockRepository.getBodyExceptSlugId(Mockito.anyString())).thenReturn(body);
        Mockito.when(this.mockRepository.findBySlug(Mockito.anyString()))
                .thenReturn(Optional.of(ArticleFactory.buildArticle(TestUtils.buildCreateArticleRequestDTO())));
        this.service.update(TestUtils.SAMPLE_SLUG_ID, requestDTO);
    }

    @Test(expected = ArticleNotFoundException.class)
    public void testGetTimeToReadArticleIfArticleNotFound() {
        Mockito.when(this.mockRepository.findBySlug(Mockito.anyString())).thenReturn(Optional.empty());
        this.service.getTimeToReadArticle(TestUtils.SAMPLE_SLUG_ID);
    }

    @Test
    public void testGetTimeToReadArticle() {
        ReflectionTestUtils.setField(this.service, "noOfWordsToReadPerMinuteByHuman", 10);
        Article article = ArticleFactory.buildArticle(TestUtils.buildCreateArticleRequestDTO());
        article.setSlug(TestUtils.SAMPLE_SLUG_ID);
        Mockito.when(this.mockRepository.findBySlug(Mockito.anyString())).thenReturn(Optional.of(article));
        TimeToReadArticleResponseDTO response = this.service.getTimeToReadArticle(TestUtils.SAMPLE_SLUG_ID);
        Assert.assertNotNull(response.getArticleId());
        Assert.assertNotNull(response.getMins());
        Assert.assertNotNull(response.getSeconds());
    }

    @Test
    public void testGetArticles() {
        Pageable pageable = PageRequest.of(0, 5);
        List<Article> articles = new ArrayList<Article>();
        articles.add(ArticleFactory.buildArticle(TestUtils.buildCreateArticleRequestDTO()));
        articles.add(ArticleFactory.buildArticle(TestUtils.buildCreateArticleRequestDTO()));
        Mockito.when(this.mockRepository.findAll(Mockito.eq(pageable)))
                .thenReturn(new PageImpl<Article>(articles, pageable, articles.size()));
        ArticlePage actualResponse = this.service.getArticles(0, 5);

        Mockito.verify(this.mockRepository).findAll(pageableCaptor.capture());
        Assert.assertEquals(pageable.getPageNumber(), pageableCaptor.getValue().getPageNumber());
        Assert.assertEquals(pageable.getPageSize(), pageableCaptor.getValue().getPageSize());
        Assert.assertEquals(articles.size(), actualResponse.getTotalResults());
        Assert.assertEquals(pageable.getPageNumber(), actualResponse.getPageNumber());
    }

}
