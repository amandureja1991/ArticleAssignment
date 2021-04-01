package com.assignment.article.exception.handler;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.assignment.article.exceptions.ArticleNotFoundException;
import com.assignment.article.exceptions.SimilarBodyAlreadyExistException;
import com.assignment.article.exceptions.SlugIdAlreadyExistsException;
import com.assignment.article.rest.utils.TestUtils;


@RunWith(SpringJUnit4ClassRunner.class)
public class ArticleExceptionHandlerTest {
    
    @InjectMocks
    private ArticleExceptionHandler exceptionHandler;

    @Test
    public void shouldReturn404OnArticleNotFoundException() {
        ResponseEntity<String> response = exceptionHandler.handleArticleNotFoundException(new ArticleNotFoundException(TestUtils.SAMPLE_SLUG_ID));
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assert.assertTrue(response.getBody().contains(TestUtils.SAMPLE_SLUG_ID));
    }
    
    @Test
    public void shouldReturn400OnSimilarBodyAlreadyExistException() {
        ResponseEntity<String> response = exceptionHandler.handleSimilarBodyAlreadyExistException(new SimilarBodyAlreadyExistException());
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertNotNull(response.getBody());
    }
    
    @Test
    public void shouldReturn400OnSlugIdAlreadyExistsException() {
        ResponseEntity<String> response = exceptionHandler.handleSlugIdAlreadyExistsException(new SlugIdAlreadyExistsException(TestUtils.SAMPLE_SLUG_ID));
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertTrue(response.getBody().contains(TestUtils.SAMPLE_SLUG_ID));
    }
}
