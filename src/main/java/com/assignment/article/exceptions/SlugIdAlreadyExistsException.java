package com.assignment.article.exceptions;

public class SlugIdAlreadyExistsException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "Slug Id : '%1$s' already exist.";

    public SlugIdAlreadyExistsException(String slugId) {
        super(String.format(EXCEPTION_MESSAGE, slugId));
    }

}
