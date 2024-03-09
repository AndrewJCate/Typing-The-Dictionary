package com.cate.typingthedictionary.exceptions;

public class WordAlreadyExistsException extends RuntimeException {

    public WordAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}
