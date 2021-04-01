package com.assignment.article.exceptions;

public class SimilarBodyAlreadyExistException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "The value of body attribute is similar to one of our existing records";

    public SimilarBodyAlreadyExistException() {
        super(EXCEPTION_MESSAGE);
    }

}
