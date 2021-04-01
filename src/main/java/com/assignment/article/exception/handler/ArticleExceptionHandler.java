package com.assignment.article.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.assignment.article.exceptions.ArticleNotFoundException;
import com.assignment.article.exceptions.SimilarBodyAlreadyExistException;
import com.assignment.article.exceptions.SlugIdAlreadyExistsException;

@ControllerAdvice
public class ArticleExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ArticleExceptionHandler.class);

    @ExceptionHandler(ArticleNotFoundException.class)
    public ResponseEntity<String> handleArticleNotFoundException(final ArticleNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SimilarBodyAlreadyExistException.class)
    public ResponseEntity<String> handleSimilarBodyAlreadyExistException(final SimilarBodyAlreadyExistException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SlugIdAlreadyExistsException.class)
    public ResponseEntity<String> handleSlugIdAlreadyExistsException(final SlugIdAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
