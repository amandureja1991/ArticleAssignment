package com.assignment.article.exceptions;

public class ArticleNotFoundException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "Artice not found with the given Slug Id : '%1$s'.";

    public ArticleNotFoundException(String slugId) {
        super(String.format(EXCEPTION_MESSAGE, slugId));
    }

}
