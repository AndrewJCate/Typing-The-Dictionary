package com.cate.typingthedictionary.exceptions;

/**
 * This custom exception is triggered by attempting to add a pre-existing word to a dictionary. This is done to
 * prevent duplicate entries from overwriting pre-existing entries.
 */
public class WordAlreadyExistsException extends RuntimeException {

    public WordAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}
